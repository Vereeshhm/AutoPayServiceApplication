package com.example.Autopayservice.Utils;

public class DebitRequest {

	private String customerAuthenticationId;
	private String amount;
	private String productInfo;
	private String firstName;
	private String phone;
	private String email;
	private String successRedirectUrl;
	private String failureRedirectUrl;
	private String callbackUrl;
	private String maxAmount;
	private String subMerchantId;
	private String finalCollectionDate;

	public String getCustomerAuthenticationId() {
		return customerAuthenticationId;
	}

	public void setCustomerAuthenticationId(String customerAuthenticationId) {
		this.customerAuthenticationId = customerAuthenticationId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSuccessRedirectUrl() {
		return successRedirectUrl;
	}

	public void setSuccessRedirectUrl(String successRedirectUrl) {
		this.successRedirectUrl = successRedirectUrl;
	}

	public String getFailureRedirectUrl() {
		return failureRedirectUrl;
	}

	public void setFailureRedirectUrl(String failureRedirectUrl) {
		this.failureRedirectUrl = failureRedirectUrl;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	



	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getSubMerchantId() {
		return subMerchantId;
	}

	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
	}

	public String getFinalCollectionDate() {
		return finalCollectionDate;
	}

	public void setFinalCollectionDate(String finalCollectionDate) {
		this.finalCollectionDate = finalCollectionDate;
	}

}
