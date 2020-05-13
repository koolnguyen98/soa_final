package com.soa.api.controller.request;

public class OrderRequest {

	private boolean paymentMethod;

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

}
