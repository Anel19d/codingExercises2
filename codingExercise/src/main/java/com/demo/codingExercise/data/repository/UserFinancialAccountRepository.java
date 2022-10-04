package com.demo.codingExercise.data.repository;

import com.demo.codingExercise.data.entity.UserFinancialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFinancialAccountRepository extends JpaRepository<UserFinancialAccount,Integer> {
}
