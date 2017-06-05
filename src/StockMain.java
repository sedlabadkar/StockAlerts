import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Sachin Edlabadkar
 */

public class StockMain {
    public static void main(String[] args){
        if (args.length == 0){
            System.out.println("Please provide the input file name.");
        } else {
            try {
                StockAlerts sa = new StockAlerts();
                Scanner sc = new Scanner(new BufferedReader(new FileReader(new File(args[0]))));

                int numStocksPurchases = sc.nextInt();
                int numFriendsList = sc.nextInt();
                int numTests = sc.nextInt();
                sc.nextLine();
                while (numStocksPurchases != 0){
                    String[] line = sc.nextLine().split(";");
                    List<String> purchaseRecord = new ArrayList<String>();
                    String userID = line[0];
                    for(int i = 1; i < line.length; i++){
                        purchaseRecord.add(line[i]);
                    }
                    sa.setUserPurchaseList(userID, purchaseRecord);
                    numStocksPurchases--;
                }
                while (numFriendsList != 0){
                    String[] line = sc.nextLine().split(";");
                    List<String> friendsList = new ArrayList<String>();

                    String userID = line[0];
                    for(int i = 1; i < line.length; i++){
                        friendsList.add(line[i]);
                    }
                    sa.setUserFriendList(userID, friendsList);
                    numFriendsList--;
                }
                while (numTests != 0){
                    String line = sc.nextLine();
                    System.out.println(line);
                    List<String> stockAlerts = sa.getStockAlertsForUser(line);
                    System.out.println("-------------------------------------------");
                    System.out.println("UserID = " + line);
                    for (String stockAlert : stockAlerts){
                        System.out.println(stockAlert);
                    }
                    System.out.println("-------------------------------------------");
                    numTests--;
                }

            } catch (FileNotFoundException fnfe){
                System.out.println(fnfe.getMessage());
            }
        }
    }
}
