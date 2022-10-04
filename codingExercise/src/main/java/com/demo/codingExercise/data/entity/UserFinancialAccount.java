package com.demo.codingExercise.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user_financial_account")
public class UserFinancialAccount  implements  Comparable<UserFinancialAccount>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "open_date", nullable = false)
    private Date openDate;

    @OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private Set<SpendingTransaction> spendingTransactions = new HashSet<>();

    public UserFinancialAccount() {
    }

    public UserFinancialAccount(Integer id, String name, Date openDate) {
        this.id = id;
        this.name = name;
        this.openDate = openDate;
    }

    @Override
    public int compareTo(UserFinancialAccount o) {
        if(this.id == o.getId())
            return 0;
        else if(this.id > o.getId())
            return 1;
        else
            return -1;
    }
}
