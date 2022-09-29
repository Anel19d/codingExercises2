package com.demo.codingExercise.data;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class UserFinancialAccount {

    private Integer id;

    private Date openDate;

    public UserFinancialAccount() {
    }

    public UserFinancialAccount(Integer id, Date date) {
        this.id = id;
        this.openDate = date;

    }
}
