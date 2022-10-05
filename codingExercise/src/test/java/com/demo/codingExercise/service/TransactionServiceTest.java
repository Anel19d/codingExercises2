package com.demo.codingExercise.service;

import com.demo.codingExercise.data.UserTotalAnnualSpend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Test
    void should_bring_the_users_total_annual_spending_is_empty() throws ParseException {
        List<UserTotalAnnualSpend> annualSpendList = new ArrayList<>();

        Mockito.when(transactionService.getUsersTotalAnnualSpending()).thenReturn(
                annualSpendList
        );
    }


    @Test
    void should_bring_the_users_total_annual_spending_() throws ParseException {
        List<UserTotalAnnualSpend> annualSpendList = new ArrayList<>();
        UserTotalAnnualSpend userTotalAnnualSpend = new UserTotalAnnualSpend(1, 1300.00);
        UserTotalAnnualSpend userTotalAnnualSpend2 = new UserTotalAnnualSpend(2, 1000.00);
        UserTotalAnnualSpend userTotalAnnualSpend3 = new UserTotalAnnualSpend(3, 4000.00);

        annualSpendList.add(userTotalAnnualSpend);
        annualSpendList.add(userTotalAnnualSpend2);
        annualSpendList.add(userTotalAnnualSpend3);

        Mockito.when(transactionService.getUsersTotalAnnualSpending()).thenReturn(
                annualSpendList
        );
        assertThat(transactionService.getUsersTotalAnnualSpending()).isNotNull();

    }

}