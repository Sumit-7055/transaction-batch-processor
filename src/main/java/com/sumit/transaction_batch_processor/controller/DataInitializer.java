package com.sumit.transaction_batch_processor.controller;

import com.sumit.transaction_batch_processor.entity.Transaction;
import com.sumit.transaction_batch_processor.entity.TransactionStatus;
import com.sumit.transaction_batch_processor.entity.TransactionType;
import com.sumit.transaction_batch_processor.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final TransactionRepository transactionRepository;

    public DataInitializer(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) {

        for (int i = 1; i <= 1000; i++) {

            Transaction transaction = new Transaction();

            transaction.setTransactionId("TXN-" + i);
            transaction.setAccountNumber("ACC-" + (1000 + i));
            transaction.setAmount(
                    BigDecimal.valueOf((i * 100) % 10000 + 100)
            );

            transaction.setType(
                    i % 2 == 0
                            ? TransactionType.CREDIT
                            : TransactionType.DEBIT
            );

            transaction.setStatus(TransactionStatus.PENDING);

            transactionRepository.save(transaction);
        }
    }
}