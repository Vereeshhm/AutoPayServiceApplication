package com.example.Autopayservice.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:src/main/resources/application.properties")
public class PropertiesConfig {

	@Value("${InitiateUrl}")
	private String InitiateUrl;
	@Value("${DebitUrl}")
	private String DebitUrl;

	@Value("${TransactionUrl}")
	private String TransactionUrl;

	@Value("${CancelUrl}")
	private String CancelUrl;

	public String getCancelUrl() {
		return CancelUrl;
	}

	public void setCancelUrl(String cancelUrl) {
		CancelUrl = cancelUrl;
	}

	public String getTransactionUrl() {
		return TransactionUrl;
	}

	public void setTransactionUrl(String transactionUrl) {
		TransactionUrl = transactionUrl;
	}

	public String getDebitUrl() {
		return DebitUrl;
	}

	public void setDebitUrl(String debitUrl) {
		DebitUrl = debitUrl;
	}

	public String getInitiateUrl() {
		return InitiateUrl;
	}

	public void setInitiateUrl(String initiateUrl) {
		InitiateUrl = initiateUrl;
	}

}
