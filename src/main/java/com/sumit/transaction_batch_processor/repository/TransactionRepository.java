package com.sumit.transaction_batch_processor.repository;

import com.sumit.transaction_batch_processor.entity.Transaction;
import com.sumit.transaction_batch_processor.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findByStatus(TransactionStatus status);

}
