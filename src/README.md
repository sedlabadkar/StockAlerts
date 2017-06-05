# Stock Alerts


## How to run?

`$ javac StockMain.java StockAlerts.java`

`$ java StockMain input.txt`

Output is printed to console

---

1) Write a function that provides a ranked (high to low) list of alerts. You can
represent an alert by a string “<net_friends>,<BUY|SELL>,<ticker>”, e.g.
“5,SELL,GOOG” to indicate a net number of 5 friends selling GOOG.
* The function `getStockAlertsForUser()` in the `StockAlerts.java` file takes userID as input and returns a ordered list of Stock Alert strings.
* `getUserTradeTransactionsForDuration()` takes userID, a start date, and an end date as input and returns the net buy/sell count for that user, for each ticker, for the specified time period.
* `getUserTradeTransactionsForLastWeek()` takes userID as input and returns a map with ticker-wise consolidation of the user's transactions for the last 7 days.
* I have also included naive implementations of the two library functions. Both these functions return the `List<String>` retrieved from a HashMap lookup, if the key is not found in the HashMap then an empty list is returned.


2) Write code for a few key unit tests for your code.
* Some basic test cases are defined in the input.txt file, this file can be passed to the StockMain.java file which acts as an interface to execute all the tests against the StockAlerts class.
* The input.txt file expects the following format:
    * First line is number of stock purchase transactions, M
    * Second line is the number of friends lists, K
    * Third line is the number of users to run the test for, N
    * This is followed by M lines of stock purchase transactions. The first value in each line is the UserID, followed by that user's transaction history separated by semicolons
    * Followed by K lines of friend list. First value is user ID, followed by friend IDs separated by semicolons
    * Lastly N lines of userIDs, the function `getStockAlertsForUser()` will be called for each of these and output will be printed on the console.
* In the included file, the following test cases are executed
    * User 1 & 2 's friend list is initialized with other users
    * User 3 is a friend of itself
    * User 4's friends does not exist
    * User 5's has a mix of real and fake friends

    * User 1 makes 2 transactions on the Ticker `ABCD` one for sell and another for buy. Both cancel the net effect of User 1 on Stock Alerts. This can been seen in the output
    * User 5's transaction history has a transaction with incorrect date, this transaction is ignored by the code with proper prints on the console.
    * User 2's transaction history has a transaction that does not specify "BUY" or "SELL", this transaction is also ignored.
    * `getStockAlertsForUser()` only considers the transaction history for last 7 days (Today to Today - 7) , there are a few transactions in the input that are outside this period, they are ignored as expected.


3) Enumerate other unit test scenarios (code not required).
* Test with larger input, hundreds of users in friends list and multiple transactions for each user.
* Another interesting test case would be to test real time application of this code/feature.


4) Provide the space and time complexity of your solution.
* If a user has n friends and each friend on average has m transactions for the past week then the time complexity of this function is O(nm)