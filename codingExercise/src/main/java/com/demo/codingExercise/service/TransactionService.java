package com.demo.codingExercise.service;

import com.demo.codingExercise.data.UserMonthlyAvg;
import com.demo.codingExercise.data.UserTotalAnnualSpend;

import java.text.ParseException;
import java.util.List;

public interface TransactionService {
    List<UserTotalAnnualSpend> getUsersTotalAnnualSpending() throws ParseException;
    List<UserMonthlyAvg> getUserMonthlyAverage() throws ParseException;

}
