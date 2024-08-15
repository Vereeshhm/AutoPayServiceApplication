package com.example.Autopayservice.Apilogentity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "api_logss")
public class ApiLog {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_logss_seq")
	@SequenceGenerator(name = "api_logss_seq", sequenceName = "api_logss_seq", allocationSize = 1)
	private Long id;

	private String url;


	@Column(columnDefinition = "TEXT")
	private String requestBody;

	@Column(columnDefinition = "TEXT")
	private String responseBody;

	private int status;

	private LocalDateTime timestamp;

	public ApiLog() {
		// Constructor logic (if any)
	}

	@PrePersist
	protected void onCreate() {
		this.timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

	// Getters and setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setResponseBodyAsJson(String message) {
		this.responseBody = "{\"message\": \"" + message.replace("\"", "\\\"") + "\"}";
	}
}
