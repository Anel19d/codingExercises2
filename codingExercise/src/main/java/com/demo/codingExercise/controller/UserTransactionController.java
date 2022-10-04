package com.demo.codingExercise.controller;

import com.demo.codingExercise.data.UserMonthlyAvg;
import com.demo.codingExercise.data.UserTotalAnnualSpend;
import com.demo.codingExercise.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * **/
@RestController
@RequestMapping("/user")
public class UserTransactionController {

    @Autowired
    TransactionService service;

    @GetMapping("/totalAnnual")
    private ResponseEntity getUserTotalAnnual(){
        try{
          List<UserTotalAnnualSpend> userTotalAnnualSpendList = service.getUsersTotalAnnualSpending();
          return new ResponseEntity(userTotalAnnualSpendList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/avgMonthly")
    private ResponseEntity getUserAvgMonthly(){
        try{
            List<UserMonthlyAvg> userMonthlyAvgList = service.getUserMonthlyAverage();
            return new ResponseEntity(userMonthlyAvgList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
