package com.demo.codingExercise.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTotalAnnualSpend {

    private Integer userAccountId;

    private Double totalAmountSpend;

    public UserTotalAnnualSpend(Integer userAccountId, Double totalAmountSpend) {
        this.userAccountId = userAccountId;
        this.totalAmountSpend = totalAmountSpend;
    }

    public UserTotalAnnualSpend() {

    }
}
