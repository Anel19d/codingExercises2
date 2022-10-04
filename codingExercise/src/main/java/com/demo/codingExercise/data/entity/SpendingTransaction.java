package com.demo.codingExercise.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "spending_transaction")
public class SpendingTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private UserFinancialAccount userAccount;

    @Column(name = "spending_amount", nullable = false)
    private Double spendingAmount;

    public SpendingTransaction() {

    }

    public SpendingTransaction(Integer id, Date transactionDate, UserFinancialAccount userAccount, Double spendingAmount) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.userAccount = userAccount;
        this.spendingAmount = spendingAmount;
    }

}
