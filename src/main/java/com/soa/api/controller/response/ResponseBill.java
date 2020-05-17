package com.soa.api.controller.response;

import java.util.List;

import com.soa.api.entity.Order;

public class ResponseBill {

	private List<Order> waitConfirm;
	
	private List<Order> confirm;
	
	private List<Order> success;
	
	private List<Order> unsuccess;

	public ResponseBill() {
		super();
	}

	public List<Order> getWaitConfirm() {
		return waitConfirm;
	}

	public void setWaitConfirm(List<Order> waitConfirm) {
		this.waitConfirm = waitConfirm;
	}

	public List<Order> getConfirm() {
		return confirm;
	}

	public void setConfirm(List<Order> confirm) {
		this.confirm = confirm;
	}

	public List<Order> getSuccess() {
		return success;
	}

	public void setSuccess(List<Order> success) {
		this.success = success;
	}

	public List<Order> getUnsuccess() {
		return unsuccess;
	}

	public void setUnsuccess(List<Order> unsuccess) {
		this.unsuccess = unsuccess;
	}
	
}
