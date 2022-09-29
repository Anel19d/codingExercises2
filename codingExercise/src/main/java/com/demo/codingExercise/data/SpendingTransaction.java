package com.demo.codingExercise.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class SpendingTransaction {

    private Integer id;

    private Date transactionDate;

    private Integer accountId;

    private Double spendingAmount;

    public SpendingTransaction() {

    }

        public SpendingTransaction(Integer id, Date transactionDate, Integer accountId, Double spendingAmount) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.accountId = accountId;
        this.spendingAmount = spendingAmount;
    }
}
