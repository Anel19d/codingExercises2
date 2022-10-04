package com.demo.codingExercise.data.repository;

import com.demo.codingExercise.data.entity.SpendingTransaction;
import com.demo.codingExercise.data.entity.UserFinancialAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class SpendingTransactionRepositoryTest {
    @Autowired
    SpendingTransactionRepository spendingTransactionRepository;

    @Autowired
    private TestEntityManager entityManager;

    SpendingTransaction spendingTransaction;

    @BeforeEach
    void setUp(){
        UserFinancialAccount userFinancialAccount = new UserFinancialAccount(1,"Alfonso Herrera", new Date(2019-12-14));
        spendingTransaction = new SpendingTransaction(1,new Date(2021-8-23), userFinancialAccount, 1000.0);

    }

    @Test
    public void should_find_all_transactions_Is_Not_Null(){
        List<SpendingTransaction> transactionList = spendingTransactionRepository.findAll();
        assertThat(transactionList).isNotNull();
    }

    @Test
    public void should_find_transactions_by_id(){
        SpendingTransaction transaction = spendingTransactionRepository.findById(1).get();
        assertThat(transaction).isNotNull();
        assertThat(transaction.getId()).isEqualTo(spendingTransaction.getId());
        assertThat(transaction.getSpendingAmount()).isEqualTo(spendingTransaction.getSpendingAmount());
    }


}