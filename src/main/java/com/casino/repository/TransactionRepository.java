package com.casino.repository;

import com.casino.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(value = "select top 10 * from Transaction where player_id = (select id from player where username = :username) order by id desc",
            nativeQuery = true)
    List<Transaction> loadLastTenTransactions(String username);
}
