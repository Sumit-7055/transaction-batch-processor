package com.sumit.transaction_batch_processor.service;

import com.sumit.transaction_batch_processor.entity.Transaction;
import com.sumit.transaction_batch_processor.entity.TransactionStatus;
import com.sumit.transaction_batch_processor.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BatchProcessingService {

    private static final int BATCH_SIZE = 100;

    private final TransactionRepository transactionRepository;

    @Autowired
    public BatchProcessingService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void processPendingTransactions() {
        long startTime = System.currentTimeMillis();

        List<Transaction> transactions =
                transactionRepository.findByStatus(TransactionStatus.PENDING);

        System.out.println("Total pending transactions: " + transactions.size());

        for (int start = 0; start < transactions.size(); start += BATCH_SIZE) {

            int end = Math.min(start + BATCH_SIZE, transactions.size());

            List<Transaction> batch =
                    transactions.subList(start, end);

            System.out.println(
                    "Processing batch: " +
                            (start / BATCH_SIZE + 1) +
                            " | Records: " +
                            batch.size()
            );

            processBatch(batch);
        }

        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;

        System.out.println("Batch processing completed.");
        System.out.println("Execution time: " + executionTime + " ms");
    }

    private void processBatch(List<Transaction> batch) {

        for (Transaction transaction : batch) {
            processTransaction(transaction);
        }
    }

    private void processTransaction(Transaction transaction) {

        try {

            transaction.setStatus(TransactionStatus.PROCESSING);
            transactionRepository.save(transaction);

            validateTransaction(transaction);

            // Simulate business processing
            transaction.setStatus(TransactionStatus.SUCCESS);
            transaction.setProcessedAt(LocalDateTime.now());

        } catch (Exception e) {

            transaction.setStatus(TransactionStatus.FAILED);
            transaction.setErrorMessage(e.getMessage());

        }

        transactionRepository.save(transaction);
    }

    private void validateTransaction(Transaction transaction) {

        if (transaction.getAmount() == null ||
                transaction.getAmount().signum() <= 0) {

            throw new IllegalArgumentException(
                    "Transaction amount must be greater than zero"
            );
        }

        if (transaction.getAccountNumber() == null ||
                transaction.getAccountNumber().isBlank()) {

            throw new IllegalArgumentException(
                    "Account number is required"
            );
        }
    }
}
