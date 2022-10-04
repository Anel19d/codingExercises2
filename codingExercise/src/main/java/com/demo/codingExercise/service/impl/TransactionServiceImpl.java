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
        List<SpendingTransaction> spendingTransactionList = spendingTransactionRepository.findAll();
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

        List<UserFinancialAccount> userFinancialList = userFinancialAccountRepository.findAll();
        List<SpendingTransaction> spendingTransactionList = spendingTransactionRepository.findAll();
                //.stream().sorted().collect(Collectors.toList());

        Map<Integer, Double> months = getMonth();

        Date pastDate = getPastYear();
        Date currentDate = new Date();

//        //sort the array for loop in order AT the date after
        spendingTransactionList
                .sort(Comparator.comparing(SpendingTransaction::getUserAccount)
                                .thenComparing(SpendingTransaction::getTransactionDate));

        //Get the first user and the first month to get a base
        int currentUser = spendingTransactionList.get(0).getUserAccount().getId();
        int currentMonth = 0;

        int idx = 1;
        for (SpendingTransaction transaction : spendingTransactionList) {
            //I validate if the date that arrives from the list is in the range of the past year
            if(transaction.getTransactionDate().after(pastDate) //no va a pasar hasta que encuentre una fecha con este rango
                    && transaction.getTransactionDate().before(currentDate)){
                if (currentUser != transaction.getUserAccount().getId()) {
                    //here, add all the avg of the months by user
                    months.put(currentMonth, getAvg(amountByMonth));
                    UserMonthlyAvg userMonthlyAvg = new UserMonthlyAvg(currentUser, months);
                    userMonthlyAvgList.add(userMonthlyAvg);
                    //and change of user
                    currentUser = transaction.getUserAccount().getId();
                    currentMonth = 0;
                    amountByMonth = new ArrayList<>();
                    months = getMonth();
                }
                if(currentMonth == 0 || currentMonth == getMonth(transaction.getTransactionDate())) {// oct
                    amountByMonth.add(transaction.getSpendingAmount());
                    currentMonth = getMonth(transaction.getTransactionDate());//oct
                    if(idx == spendingTransactionList.size()){
                        months.put(currentMonth, getAvg(amountByMonth));
                        UserMonthlyAvg userMonthlyAvg = new UserMonthlyAvg(currentUser, months);
                        userMonthlyAvgList.add(userMonthlyAvg);
                    }

                } else {
                    months.put(currentMonth, getAvg(amountByMonth));
                    amountByMonth = new ArrayList<>();
                    amountByMonth.add(transaction.getSpendingAmount());//agrego el valor del mes en curso dic
                    currentMonth = getMonth(transaction.getTransactionDate());//dic
                }
            }
            idx++;
        }
        return userMonthlyAvgList;

    }

    public void setNameForMonths() {
    }

//    private Date getConvertToDate(String strDate) throws ParseException {
//        Date date = new SimpleDateFormat("yyyy-MM-dd")
//                .parse(strDate);
//
//        return date;
//    }

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
