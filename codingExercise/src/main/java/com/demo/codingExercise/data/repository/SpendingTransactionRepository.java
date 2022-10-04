package com.demo.codingExercise.data.repository;

import com.demo.codingExercise.data.entity.SpendingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpendingTransactionRepository extends JpaRepository<SpendingTransaction, Integer> {
}
