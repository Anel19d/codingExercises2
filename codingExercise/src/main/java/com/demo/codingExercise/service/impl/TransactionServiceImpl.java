package com.demo.codingExercise.service.impl;

import com.demo.codingExercise.data.SpendingTransaction;
import com.demo.codingExercise.data.UserFinancialAccount;
import com.demo.codingExercise.data.UserMonthlyAvg;
import com.demo.codingExercise.data.UserTotalAnnualSpend;
import com.demo.codingExercise.service.TransactionService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Override
    public List<UserTotalAnnualSpend> getUsersTotalAnnualSpending() throws ParseException {

        List<UserFinancialAccount> userFinancialList = getUserFinancialAccount();
        List<SpendingTransaction> spendingTransactionList = getSpendingTransaction();
        List<UserTotalAnnualSpend> userTotalAnnualSpendList = new ArrayList<>();

        Date pastDate = getPastYear();
        Date currentDate = new Date();

        for (UserFinancialAccount user : userFinancialList) {
            Double total = spendingTransactionList.stream()
                    .filter(t ->  t.getAccountId().equals(user.getId()) &&
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

        List<UserFinancialAccount> userFinancialList = getUserFinancialAccount();
        List<SpendingTransaction> spendingTransactionList = getSpendingTransaction();

        Map<Integer, Double> months = getMonth();

        Date pastDate = getPastYear();
        Date currentDate = new Date();

        //sort the array for loop in order
        spendingTransactionList
                .sort(Comparator.comparing(SpendingTransaction::getAccountId)
                        .thenComparing(SpendingTransaction::getTransactionDate));

        //Get the first user and the first month to get a base
        int currentUser = spendingTransactionList.get(0).getAccountId();


        for (SpendingTransaction transaction : spendingTransactionList) {
            //I validate if the date that arrives from the list is in the range of the past year
            int currentMonth = getMonth(transaction.getTransactionDate());

            if(transaction.getTransactionDate().after(pastDate)
                    && transaction.getTransactionDate().before(currentDate)){

                if (currentUser == transaction.getAccountId()
                        && currentMonth == getMonth(transaction.getTransactionDate())) {

                    amountByMonth.add(transaction.getSpendingAmount());

                } else if (currentUser == transaction.getAccountId()
                        && currentMonth != getMonth(transaction.getTransactionDate())) {

                    if(amountByMonth.isEmpty()){
                        amountByMonth.add(transaction.getSpendingAmount());
                    }

                    //here, add the current mont with the avg
                    months.put(currentMonth, getAvg(amountByMonth));

                    //here, initialize the variables
                    currentMonth = getMonth(transaction.getTransactionDate());
                    amountByMonth = new ArrayList<>();
                    amountByMonth.add(transaction.getSpendingAmount());

                } else if (currentUser != transaction.getAccountId()) {
                    //here, add all the avg of the months by user
                    UserMonthlyAvg userMonthlyAvg = new UserMonthlyAvg(currentUser, months);
                    userMonthlyAvgList.add(userMonthlyAvg);
                    //and change of user
                    currentUser = transaction.getAccountId();
                }
            }
        }
        return userMonthlyAvgList;

    }

    private List<UserFinancialAccount> getUserFinancialAccount() throws ParseException {
        List<UserFinancialAccount> userFinancialList = new ArrayList<>();
        userFinancialList.add(new UserFinancialAccount(1, getConvertToDate("2020-08-20")));
        userFinancialList.add(new UserFinancialAccount(2, getConvertToDate("2022-09-15")));

        return userFinancialList;
    }

    private List<SpendingTransaction> getSpendingTransaction() throws ParseException {
        List<SpendingTransaction> spendingTransactionList = new ArrayList<>();

        spendingTransactionList.add(new SpendingTransaction(1, getConvertToDate("2021-08-20"),
                1, 100.0));
        spendingTransactionList.add(new SpendingTransaction(1, getConvertToDate("2021-08-23"),
                1, 1000.0));
        spendingTransactionList.add(new SpendingTransaction(2,getConvertToDate("2021-09-11"),
                1, 2000.0));
        spendingTransactionList.add(new SpendingTransaction(3,getConvertToDate("2021-10-10"),
                1, 500.0));
        spendingTransactionList.add(new SpendingTransaction(4,getConvertToDate("2021-12-01"),
                1, 5000.0));
        spendingTransactionList.add(new SpendingTransaction(5,getConvertToDate("2022-03-01"),
                1, 8000.0));
        spendingTransactionList.add(new SpendingTransaction(5,getConvertToDate("2022-05-18"),
                1, 9000.0));
        spendingTransactionList.add(new SpendingTransaction(5,getConvertToDate("2022-05-18"),
                1, 200.0));
        spendingTransactionList.add(new SpendingTransaction(5,getConvertToDate("2022-09-27"),
                1, 200.0));
        spendingTransactionList.add(new SpendingTransaction(3,getConvertToDate("2022-09-16"),
                2, 500.0));
        spendingTransactionList.add(new SpendingTransaction(4,getConvertToDate("2022-09-17"),
                2, 300.0));
        spendingTransactionList.add(new SpendingTransaction(5,getConvertToDate("2022-09-20"),
                2, 200.0));
        spendingTransactionList.add(new SpendingTransaction(5,getConvertToDate("2022-09-27"),
                2, 4000.0));
        return spendingTransactionList;
    }


    private Date getConvertToDate(String strDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd")
                .parse(strDate);

        return date;
    }

    private  Date getPastYear(){

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.HOUR, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        Date d = calendar.getTime();
        System.out.println(d);

        return calendar.getTime();
    }

    private static Double getAvg(List<Double> transactionAmounts){
        return transactionAmounts.stream()
                .mapToDouble(Double::doubleValue)
                .average().getAsDouble();
    }

    private static Integer getMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    private  Map<Integer, Double> getMonth(){
        Map<Integer, Double> months = new HashMap<>();
        //  Month   Avg
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
