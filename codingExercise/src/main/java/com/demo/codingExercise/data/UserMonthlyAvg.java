package com.demo.codingExercise.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UserMonthlyAvg {
    private Integer userId;

    private Map<Integer, Double> avgMonthly;

    public UserMonthlyAvg() {
    }

    public UserMonthlyAvg(Integer userId, Map<Integer, Double> avgMonthly) {
        this.userId = userId;
        this.avgMonthly = avgMonthly;
    }
}
