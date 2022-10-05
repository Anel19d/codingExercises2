package com.demo.codingExercise.service.impl;

import com.demo.codingExercise.data.entity.SpendingTransaction;
import com.demo.codingExercise.data.entity.UserFinancialAccount;
import com.demo.codingExercise.data.UserMonthlyAvg;
import com.demo.codingExercise.data.UserTotalAnnualSpend;
import com.demo.codingExercise.data.repository.SpendingTransactionRepository;
import com.demo.codingExercise.data.repository.UserFinancialAccountRepository;
import com.demo.codingExercise.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Algorithm to:
 ** Get the total annual spending transactions by user:
 *   * First all user accounts are obtained, as well as all transactions.
 *   * Calculate `pastDate ` which is a date from the past year based on the current date, to cover a full year.
 *   * Gets the user data, is necessary to iterate through the entire list.
 *   * In the list of transactions, filters by three aspects:
 *     * By userId,
 *       selects only the user's transactions from the list and avoids the others that do not correspond to the id.
 *     * By `pastDate`, bring all the transactions after this date.
 *     * By `currentDate`, bring all the transactions before this date.
 *   * Get from the list only the amount for each transaction.
 *   * And then, the sum of all amounts obtained in the previous step is stored in the `total` variable.
 *   * In the end, these results are added to this list `userTotalAnnualSpendList`
 * * This is done for each user.
 *
 * * Get the average monthly average in the past year:
 *   * First all user accounts are obtained, as well as all transactions.
 *   * Calculate `pastDate` which is a date from the past year based on the current date, to cover a full year.
 *   * `spendingTransactionList` must be ordered by user id and then by date.
 *   * Get the currentUser and `currentMonth` based on the first item of `spendingTransactionList` since these variables
 *   *  Then a loop is made to iterate the variable spendingTransactionList which is conditioned to the fact that the date
 *      of the transaction is in the date range.
 *     * By `pastDate`, bring all the transactions after this date.
 *     * By `currentDate`, bring all the transactions before this date.
 *   * Case 1: If this condition is met, we verify that the user is still
 *       the same to continue storing the monthly averages per user.
 *   * Otherwise it means that the transactions of currentUser have finished and the following must be done:
 *     * The average of the last month is calculated, and it is saved in our `month's map`, in which it is indicated that
 *      the key will be the `currentMonth` and the value the result of the average.
 *     * The averages saved in `months` are stored in `userMonthlyAvgList`
 *       these are the averages of the currentUser.
 *     * We reset the variables, which indicates that we have to  count the averages of a new user:
 *       * `currentUser`, with the new user of the current transaction.
 *       * `currentMonth` to 0
 *       * `amountByMonth` with a new fix
 *       * `months` with a catalog of months but with the values empty.
 *   * Case 2: Indicates that it is still the same month or that it has been initialized a new user and the amount of
 *     the current transaction must be added to the amountByMonth array.
 *     *   If it is the last transaction, it means that you have to calculate the
 *         running average, add it to `months` catalog and finally add it to the `userMonthlyAvgList`.
 *   * Case 3: when the month changes, the cumulative average of the currentMonth must be calculated and added to the
 *     `month`'s catalog. Also, the first transaction of the new month is saved as well as the new month
 *      in the corresponding variables.
 *   * For each lap, the start is being increased to know when the end is and that the average of the last
 *   * current month can be saved this is `idx`.
 *   * At the end just return the `userMonthlyAvgList`.
 *
 * */

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private SpendingTransactionRepository spendingTransactionRepository;

    @Autowired
    private UserFinancialAccountRepository userFinancialAccountRepository;

    @Override
    public List<UserTotalAnnualSpend> getUsersTotalAnnualSpending() throws ParseException {

        List<UserFinancialAccount> userFinancialList = Optional.ofNullable(userFinancialAccountRepository.findAll())
                .orElseThrow(() -> new IllegalStateException("Null exception"));
        List<SpendingTransaction> spendingTransactionList = Optional.ofNullable(spendingTransactionRepository.findAll())
                .orElseThrow(() -> new IllegalStateException("Null exception"));
        List<UserTotalAnnualSpend> userTotalAnnualSpendList = new ArrayList<>();

        Date pastDate = getPastYear();
        Date currentDate = new Date();

        for (UserFinancialAccount user : userFinancialList) {
            Double total = spendingTransactionList.stream()
                    .filter(t ->  t.getUserAccount().getId().equals(user.getId()) &&
                            t.getTransactionDate().after(pastDate) &&
                            t.getTransactionDate().before(currentDate))
                    .map(SpendingTransaction::getSpendingAmount)
                    .mapToDouble(Double::doubleValue).sum();

            userTotalAnnualSpendList.add(new UserTotalAnnualSpend(user.getId(), total));
        }

        return userTotalAnnualSpendList;
    }

    @Override
    public List<UserMonthlyAvg> getUserMonthlyAverage() throws ParseException {
        List<Double> amountByMonth = new ArrayList<>();

        List<UserMonthlyAvg> userMonthlyAvgList = new ArrayList<>();

        //Gets all the users accounts
        List<UserFinancialAccount> userFinancialList = userFinancialAccountRepository.findAll();
        List<SpendingTransaction> spendingTransactionList = spendingTransactionRepository.findAll();

        //variable that initialize the catalog of the months
        Map<Integer, Double> months = getMonth();

        //Gets the past date
        Date pastDate = getPastYear();
        //Gets the current date
        Date currentDate = new Date();

        //sort the array to loop in order at the date after
        spendingTransactionList
                .sort(Comparator.comparing(SpendingTransaction::getUserAccount)
                                .thenComparing(SpendingTransaction::getTransactionDate));

        //Get the first user and the first month to get a base
        int currentUser = spendingTransactionList.get(0).getUserAccount().getId();
        //it is initialized to 0
        int currentMonth = 0;

        //it is a counter of iterations
        int idx = 1;
        for (SpendingTransaction transaction : spendingTransactionList) {
            //Validates if the date that arrives from the list is in the range of the past year
            if(transaction.getTransactionDate().after(pastDate) //it's not going to let it in until it finds a date with this range
                    && transaction.getTransactionDate().before(currentDate)){
                if (currentUser != transaction.getUserAccount().getId()) {
                    //here, add all the avg of the months by user
                    months.put(currentMonth, getAvg(amountByMonth));
                    //creates the new object with the months and their values found, and also is added the current user.
                    UserMonthlyAvg userMonthlyAvg = new UserMonthlyAvg(currentUser, months);
                    //add to the list that are going to return all the averages by user
                    userMonthlyAvgList.add(userMonthlyAvg);
                    //change of user
                    currentUser = transaction.getUserAccount().getId();
                    //set current month in 0 again
                    currentMonth = 0;
                    //initialize the arrayList for amountByMonth
                    amountByMonth = new ArrayList<>();
                    //Initialized the map adding all the months as keys and the value empty
                    months = getMonth();
                }
                //case 1: indicates that it is still the same month or that it was initialized
                // a new user and the amount of the current transaction must be added
                if(currentMonth == 0 || currentMonth == getMonth(transaction.getTransactionDate())) {
                    amountByMonth.add(transaction.getSpendingAmount());
                    currentMonth = getMonth(transaction.getTransactionDate());
                    //but, If it is the last transaction, it means that you have to calculate the
                    if(idx == spendingTransactionList.size()){
                        //add it to our map called months.
                        months.put(currentMonth, getAvg(amountByMonth)); //avg is a method that helps to calculate de average from all the gave list
                        UserMonthlyAvg userMonthlyAvg = new UserMonthlyAvg(currentUser, months);
                        userMonthlyAvgList.add(userMonthlyAvg);//and finally, add it to the user list
                    }

                    //case 2: when the month changes, being so, it must compute the running average of the current month.
                } else {
                    //add it to our map called months.
                    months.put(currentMonth, getAvg(amountByMonth));
                    //initialized the arraylist
                    amountByMonth = new ArrayList<>();
                    amountByMonth.add(transaction.getSpendingAmount());//adds the value of the current month
                    currentMonth = getMonth(transaction.getTransactionDate());//set the variable as the current month
                }
            }
            //increments the index
            idx++;
        }
        return userMonthlyAvgList;

    }

    public void setNameForMonths() {
    }

    /**Allows to bring one date according other date*/
    private  Date getPastYear(){

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        Date d = calendar.getTime();
        System.out.println(d);

        return calendar.getTime();
    }

    /**Allows to calculate the average from one list*/
    private static Double getAvg(List<Double> transactionAmounts){
        return transactionAmounts.stream()
                .mapToDouble(Double::doubleValue)
                .average().getAsDouble();
    }

    /**Helps to get only the month from one date*/
    private static Integer getMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**This is a dictionary that store all the months since Jan to dec, the
     * month is represented by one number, ex. 1 = Jan, 2= feb...
     * The value will be the average.
     * */
    private  Map<Integer, Double> getMonth(){
        Map<Integer, Double> months = new HashMap<>();
        //       Month   Avg
        months.put(1, 0.0);
        months.put(2, 0.0);
        months.put(3, 0.0);
        months.put(4, 0.0);
        months.put(5, 0.0);
        months.put(6, 0.0);
        months.put(7, 0.0);
        months.put(8, 0.0);
        months.put(9, 0.0);
        months.put(10, 0.0);
        months.put(11, 0.0);
        months.put(12, 0.0);

        return months;
    }


}
