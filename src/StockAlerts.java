import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sachin Edlabadkar
 */

public class StockAlerts {
    private HashMap<String, List<String>> userFriendList, userPurchaseList;

    public StockAlerts(){
        userFriendList = new HashMap<>();
        userPurchaseList = new HashMap<>();
    }

    public boolean setUserPurchaseList(String userID, List<String> stockTransaction) {
        userPurchaseList.put(userID, stockTransaction);
        return true;
    }

    public boolean setUserFriendList(String userID, List<String> friendList) {
        userFriendList.put(userID,friendList);
        return true;
    }

    private List<String> getTradeTransactionsForUser(String userID){
        return userPurchaseList.getOrDefault(userID, Collections.emptyList());
    }

    private List<String> getFriendsListForUser(String userID){
        return userFriendList.getOrDefault(userID, Collections.emptyList());
    }

    private Map<String, Integer> getUserTradeTransactionsForDuration(String userID, Date startDate, Date endDate) {
        Map<String, Integer> netTransactionHistory = new HashMap<>();
        List<String> tradeHistory = getTradeTransactionsForUser(userID);
        for(String s : tradeHistory){
            String[] transactionString = s.split(",");
            if (transactionString.length != 3){
                continue; //ill-formed string
            }
            try{
                Date tradeDate = new SimpleDateFormat("yyyy-MM-dd").parse(transactionString[0]);
                if (tradeDate != null) {
                    if (tradeDate.before(startDate)){
                        continue;
                    } else if(tradeDate.after(endDate)){
                        break;
                    } else {
                        Integer value = netTransactionHistory.getOrDefault(transactionString[2], 0);
                        if (transactionString[1].trim().toLowerCase().equals("sell")){
                            netTransactionHistory.put(transactionString[2], --value);
                        } else if (transactionString[1].trim().toLowerCase().equals("buy")) {
                            netTransactionHistory.put(transactionString[2], ++value);
                        }
                    }
                }
            } catch (ParseException pe){
                System.out.println("Incorrect Date Format");
                System.out.println(pe.getMessage());
            }
        }
        return netTransactionHistory;
    }

    private Map<String, Integer> getUserTradeTransactionsForLastWeek(String userID){
        Calendar instance = Calendar.getInstance();
        Date today = instance.getTime();
        instance.add(Calendar.DATE, -7);
        return getUserTradeTransactionsForDuration(userID, instance.getTime(), today);
    }

    //Utility function to sort the net number of friends trading a stock map into a list of sorted map entries
    private List<Map.Entry<String, Integer>> sortMapByValueDesc(Map<String, Integer> map){
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (Math.abs(o2.getValue())) - Math.abs(o1.getValue());
            }
        });

        return list;
    }

    public List<String> getStockAlertsForUser(String userID){
        HashMap<String, Integer> friendStocksCountMap = new HashMap<>();
        List<String> stockAlerts = new ArrayList<>();

        for (String friendID : getFriendsListForUser(userID)){

            if (friendID.equals(userID)) continue;
            Map<String, Integer> friendsNetTransaction = getUserTradeTransactionsForLastWeek(friendID);
            for(String ticker : friendsNetTransaction.keySet()){
                Integer netTransaction = friendsNetTransaction.get(ticker);
                Integer friendsCount = friendStocksCountMap.getOrDefault(ticker, 0);
                if (netTransaction > 0){
                    friendsCount += 1;
                } else if (netTransaction < 0){
                    friendsCount -= 1;
                }
                friendStocksCountMap.put(ticker, friendsCount);
            }
        }

        List<Map.Entry<String, Integer>> sortedStocksMap = sortMapByValueDesc(friendStocksCountMap);

        for(Map.Entry<String, Integer> s : sortedStocksMap){
            Integer netUsers = s.getValue();
            if (netUsers > 0){
                stockAlerts.add(netUsers + ",BUY," + s.getKey());
            } else if (netUsers < 0){
                stockAlerts.add(Math.abs(netUsers) + ",SELL," + s.getKey());
            }
        }

        return stockAlerts;
    }
}
