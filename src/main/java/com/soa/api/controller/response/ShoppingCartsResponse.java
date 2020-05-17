package com.soa.api.controller.response;

import java.util.List;

import com.soa.api.entity.ShoppingCart;
import com.soa.api.entity.User;

public class ShoppingCartsResponse {
	
	private List<ShoppingCart> shoppingCarts;
	
	private double totalPrice;
	
	private User user;
	
	public ShoppingCartsResponse() {
		super();
	}

	public List<ShoppingCart> getShoppingCarts() {
		return shoppingCarts;
	}

	public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
		this.shoppingCarts = shoppingCarts;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
