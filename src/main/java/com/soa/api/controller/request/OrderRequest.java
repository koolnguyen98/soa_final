package com.soa.api.controller.request;

public class OrderRequest {

	private boolean paymentMethod;
	
	private String stress;

	public OrderRequest(boolean paymentMethod) {
		super();
		this.paymentMethod = paymentMethod;
	}

	public OrderRequest() {
		super();
	}

	public boolean getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(boolean paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStress() {
		return stress;
	}

	public void setStress(String stress) {
		this.stress = stress;
	}

}
