package com.example.Autopayservice.service;

import org.springframework.http.ResponseEntity;

import com.example.Autopayservice.Utils.CancelRequest;
import com.example.Autopayservice.Utils.DebitRequest;
import com.example.Autopayservice.Utils.InitiateRequest;
import com.example.Autopayservice.Utils.TransactionStatusRequest;

public interface AutopayService {

	ResponseEntity<String> fetchCancelrequest(CancelRequest cancelRequest);

	ResponseEntity<String> fetchInitiate(InitiateRequest initiateRequest);

	ResponseEntity<String> fetchDebitRequest(DebitRequest debitRequest);

	ResponseEntity<String> fetchTransactionstatus(TransactionStatusRequest request);

	

}
