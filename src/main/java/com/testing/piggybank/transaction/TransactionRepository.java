package com.testing.piggybank.transaction;

import com.testing.piggybank.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByReceiverAccount_Id(long receiverAccountId);


}

