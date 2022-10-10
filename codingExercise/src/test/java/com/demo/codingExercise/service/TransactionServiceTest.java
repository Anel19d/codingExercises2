package com.demo.codingExercise.service;

import com.demo.codingExercise.data.UserMonthlyAvg;
import com.demo.codingExercise.data.UserTotalAnnualSpend;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
class TransactionServiceTest {

    private final List<UserTotalAnnualSpend> annualSpendList = new ArrayList<>();
    private final  Map<Integer, Double> months = new HashMap<>();
    private final List<UserMonthlyAvg> userMonthlyAvgList = new ArrayList<>();


    @BeforeEach
    void setUpList(){
        UserTotalAnnualSpend userTotalAnnualSpend = new UserTotalAnnualSpend(1, 1300.00);
        UserTotalAnnualSpend userTotalAnnualSpend2 = new UserTotalAnnualSpend(2, 1000.00);
        UserTotalAnnualSpend userTotalAnnualSpend3 = new UserTotalAnnualSpend(3, 4000.00);

        this.annualSpendList.add(userTotalAnnualSpend);
        this.annualSpendList.add(userTotalAnnualSpend2);
        this.annualSpendList.add(userTotalAnnualSpend3);
    }
    @Test
    void should_bring_the_users_total_annual_spending_() throws ParseException {
        TransactionService transactionService = Mockito.mock(TransactionService.class);

        //when everything works well the result should return a list with the user accounts
        //and the total amount spend for a year
        Mockito.when(transactionService.getUsersTotalAnnualSpending()).thenReturn(
                this.annualSpendList
        );
        //the result doesn't null
        assertThat(transactionService.getUsersTotalAnnualSpending()).isNotNull();
        //the result is equals to the annual expected list
        assertThat(transactionService.getUsersTotalAnnualSpending()).isEqualTo(annualSpendList);

    }

    @Test
    void should_not_bring_the_users_total_annual_spending_() throws ParseException {
        TransactionService transactionService = Mockito.mock(TransactionService.class);
        //when the service doesn't contain any result returns an empty array
        Mockito.when(transactionService.getUsersTotalAnnualSpending()).thenReturn(
                new ArrayList<>());
        assertThat(transactionService.getUsersTotalAnnualSpending()).isNotNull();
        //the result is not equals to the annual expected list
        assertThat(transactionService.getUsersTotalAnnualSpending()).isNotEqualTo(this.annualSpendList);

    }

    @BeforeEach
    void setUpMap(){
        //            Month   Avg
        this.months.put(1, 0.0);
        this.months.put(2, 0.0);
        this.months.put(3, 0.0);
        this.months.put(4, 0.0);
        this.months.put(5, 0.0);
        this.months.put(6, 0.0);
        this.months.put(7, 0.0);
        this.months.put(8, 0.0);
        this.months.put(9, 0.0);
        this.months.put(10, 800.0);
        this.months.put(11, 0.0);
        this.months.put(12, 0.0);

        this.userMonthlyAvgList.add(new UserMonthlyAvg(1, months));
    }

    @Test
    void should_bring_user_monthly_average() throws ParseException {
        TransactionService transactionService = Mockito.mock(TransactionService.class);

        Mockito.when(transactionService.getUserMonthlyAverage())
                .thenReturn(this.userMonthlyAvgList);

        assertThat(transactionService.getUserMonthlyAverage()).isNotNull();

        assertThat(transactionService.getUserMonthlyAverage())
                .isEqualTo(this.userMonthlyAvgList);
    }

}