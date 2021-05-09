package com.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    @Modifying
    @Query("update Transaction t set t.status = :status where t.externalId=: externalId")
    void updateTransaction(String externalId,String status);

    Transaction findByExternalId(String externalId);
}
