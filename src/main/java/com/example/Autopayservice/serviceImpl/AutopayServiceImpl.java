package com.example.Autopayservice.serviceImpl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.Autopayservice.Apilogentity.ApiLog;
import com.example.Autopayservice.Repository.ApiLogRepository;
import com.example.Autopayservice.Utils.CancelRequest;
import com.example.Autopayservice.Utils.DebitRequest;
import com.example.Autopayservice.Utils.InitiateRequest;
import com.example.Autopayservice.Utils.PropertiesConfig;
import com.example.Autopayservice.Utils.TransactionStatusRequest;
import com.example.Autopayservice.service.AutopayService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AutopayServiceImpl implements AutopayService {

	private final RestTemplate restTemplate;
	private final String token;
	private String customerAuthenticationId;
	private String txnId;

	private static final Logger logger = LoggerFactory.getLogger(AutopayServiceImpl.class);

	@Autowired
	private ApiLogRepository apiLogRepository;

	@Autowired
	private PropertiesConfig config;

	public AutopayServiceImpl(RestTemplate restTemplate, @Value("${token}") String token) {
		this.restTemplate = restTemplate;
		this.token = token;
	}

	private ResponseEntity<String> handleResponse(String url, String requestBody, String responseBody,
			HttpStatus status) throws Exception {
		logApi(url, requestBody, responseBody, status);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(responseBody);

		if (rootNode.has("error")) {
			JsonNode errorNode = rootNode.get("error");
			int statusCode = errorNode.get("status").asInt();
			return ResponseEntity.status(statusCode).body("Failure: " + responseBody);
		}

		return ResponseEntity.ok("Success: " + responseBody);
	}

	public void logApi(String url, String requestBody, String responseBody, HttpStatus status) {
		ApiLog apiLog = new ApiLog();

		apiLog.setUrl(url);
		apiLog.setRequestBody(requestBody);
		apiLog.setResponseBody(responseBody);
		apiLog.setStatus(status.value());
		apiLog.setTimestamp(LocalDateTime.now());
		apiLogRepository.save(apiLog);

		logger.info("API: {}, Status Code: {}, Status: {}, Request: {}, Response: {}", url, status.value(),
				status.getReasonPhrase(), requestBody, responseBody);
	}

	@Override
	public ResponseEntity<String> fetchInitiate(InitiateRequest initiateRequest) {
		String url = config.getInitiateUrl();
		try {
			ObjectMapper mapper = new ObjectMapper();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			headers.set("Content-Type", "application/json");
			HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(initiateRequest), headers);

			String response = restTemplate.postForObject(url, requestEntity, String.class);

			JsonNode rootNode = mapper.readTree(response);
			customerAuthenticationId = rootNode.path("result").path("customerAuthenticationId").asText();

			logger.info("Response body: " + response);
			logger.info("Extracted CustomerAuthenticationId: " + customerAuthenticationId);
			return handleResponse(url, requestEntity.getBody(), response, HttpStatus.OK);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			String responseBody = e.getResponseBodyAsString() != null ? e.getResponseBodyAsString()
					: "No response body";
			HttpStatus statusCode = e.getStatusCode() != null ? (HttpStatus) e.getStatusCode()
					: HttpStatus.INTERNAL_SERVER_ERROR;
			logApi(url, initiateRequest.toString(), responseBody, statusCode);
			return ResponseEntity.status(statusCode).body("Failure: " + responseBody);
		} catch (Exception e) {
			logApi(url, initiateRequest.toString(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failure: " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> fetchDebitRequest(DebitRequest debitRequest) {
		String url = config.getDebitUrl();
		try {
			ObjectMapper mapper = new ObjectMapper();
			debitRequest.setCustomerAuthenticationId(customerAuthenticationId);

			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			headers.set("Content-Type", "application/json");
			HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(debitRequest), headers);

			String response = restTemplate.postForObject(url, requestEntity, String.class);
			JsonNode rootNode = mapper.readTree(response);
			txnId = rootNode.path("result").path("txnId").asText();

			logger.info("Response body: " + response);
			logger.info("Extracted txnId: " + txnId);
			return handleResponse(url, requestEntity.getBody(), response, HttpStatus.OK);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			String responseBody = e.getResponseBodyAsString() != null ? e.getResponseBodyAsString()
					: "No response body";
			HttpStatus statusCode = e.getStatusCode() != null ? (HttpStatus) e.getStatusCode()
					: HttpStatus.INTERNAL_SERVER_ERROR;
			logApi(url, debitRequest.toString(), responseBody, statusCode);
			return ResponseEntity.status(statusCode).body("Failure: " + responseBody);
		} catch (Exception e) {
			logApi(url, debitRequest.toString(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failure: " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> fetchTransactionstatus(TransactionStatusRequest request) {
		String url = config.getTransactionUrl();
		try {
			request.setTxnId(txnId);

			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			headers.set("Content-Type", "application/json");
			HttpEntity<String> requestEntity = new HttpEntity<>(new ObjectMapper().writeValueAsString(request),
					headers);

			String response = restTemplate.postForObject(url, requestEntity, String.class);

			logger.info("Response body: " + response);
			return handleResponse(url, requestEntity.getBody(), response, HttpStatus.OK);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			String responseBody = e.getResponseBodyAsString() != null ? e.getResponseBodyAsString()
					: "No response body";
			HttpStatus statusCode = e.getStatusCode() != null ? (HttpStatus) e.getStatusCode()
					: HttpStatus.INTERNAL_SERVER_ERROR;
			logApi(url, request.toString(), responseBody, statusCode);
			return ResponseEntity.status(statusCode).body("Failure: " + responseBody);
		} catch (Exception e) {
			logApi(url, request.toString(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failure: " + e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> fetchCancelrequest(CancelRequest cancelRequest) {
		String url = config.getCancelUrl();
		try {
			cancelRequest.setCustomerAuthenticationId(customerAuthenticationId);
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);
			headers.set("Content-Type", "application/json");
			HttpEntity<String> requestEntity = new HttpEntity<>(new ObjectMapper().writeValueAsString(cancelRequest),
					headers);

			String response = restTemplate.postForObject(url, requestEntity, String.class);

			logger.info("Response body: " + response);
			return handleResponse(url, requestEntity.getBody(), response, HttpStatus.OK);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			String responseBody = e.getResponseBodyAsString() != null ? e.getResponseBodyAsString()
					: "No response body";
			HttpStatus statusCode = e.getStatusCode() != null ? (HttpStatus) e.getStatusCode()
					: HttpStatus.INTERNAL_SERVER_ERROR;
			logApi(url, cancelRequest.toString(), responseBody, statusCode);
			return ResponseEntity.status(statusCode).body("Failure: " + responseBody);
		} catch (Exception e) {
			logApi(url, cancelRequest.toString(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failure: " + e.getMessage());
		}
	}

}
