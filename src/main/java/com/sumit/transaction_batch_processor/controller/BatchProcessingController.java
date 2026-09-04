package com.sumit.transaction_batch_processor.controller;

import com.sumit.transaction_batch_processor.service.BatchProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class BatchProcessingController {

    private final BatchProcessingService batchProcessingService;

    public BatchProcessingController(
            BatchProcessingService batchProcessingService) {

        this.batchProcessingService = batchProcessingService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processBatch() {

        batchProcessingService.processPendingTransactions();

        return ResponseEntity.ok(
                "Batch processing completed successfully"
        );
    }
}
