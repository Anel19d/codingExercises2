package com.demo.codingExercise.data.repository;

import com.demo.codingExercise.data.entity.SpendingTransaction;
import com.demo.codingExercise.data.entity.UserFinancialAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
class UserFinancialAccountRepositoryTest {
    @Autowired
    UserFinancialAccountRepository userFinancialAccountRepository;

    UserFinancialAccount userAccount;


    @BeforeEach
    void setUp() throws ParseException {
        userAccount = new UserFinancialAccount(1,
                "Alfonso Herrera",
                new Date(2019-12-14));
    }
    @Test
    public void should_find_all_users(){

        List<UserFinancialAccount> userFinancialAccounts = new ArrayList<>();
        userFinancialAccounts.add(userAccount);

        List<UserFinancialAccount> userFinancialAccountList = userFinancialAccountRepository.findAll();
        assertThat(userFinancialAccountList).isNotNull();
    }

    @Test
    public void should_find_By_One(){
        UserFinancialAccount userFinancialAccount =
                userFinancialAccountRepository.findById(1).get();
        assertThat(userFinancialAccount.getId()).isEqualTo(userAccount.getId());
        assertThat(userFinancialAccount.getName()).isEqualTo(userAccount.getName());
    }

}