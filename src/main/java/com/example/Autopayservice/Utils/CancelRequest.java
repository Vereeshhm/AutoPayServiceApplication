package com.example.Autopayservice.Utils;

public class CancelRequest {

	
	private String customerAuthenticationId;

	public String getCustomerAuthenticationId() {
		return customerAuthenticationId;
	}

	public void setCustomerAuthenticationId(String customerAuthenticationId) {
		this.customerAuthenticationId = customerAuthenticationId;
	}

	@Override
	public String toString() {
		return "CancelRequest [customerAuthenticationId=" + customerAuthenticationId + "]";
	}
	
	
	
}
