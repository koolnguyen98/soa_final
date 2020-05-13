package com.soa.api.controller.response;

import java.util.List;

import com.soa.api.entity.FurnitureType;
import com.soa.api.entity.Product;
import com.soa.api.entity.ShoppingCart;

public class ShopResponse {

	private String userName;
	
	private boolean admin;
	
	private List<ShoppingCart> shoppingCarts;
	
	private List<FurnitureType> furnitureTypes;
	
	private FurnitureType furnitureRoom;
	
	private List<Product> products;
	
	private Product product;

	public ShopResponse() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public List<ShoppingCart> getShoppingCarts() {
		return shoppingCarts;
	}

	public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
		this.shoppingCarts = shoppingCarts;
	}

	public List<FurnitureType> getFurnitureTypes() {
		return furnitureTypes;
	}

	public void setFurnitureTypes(List<FurnitureType> furnitureTypes) {
		this.furnitureTypes = furnitureTypes;
	}

	public FurnitureType getFurnitureRoom() {
		return furnitureRoom;
	}

	public void setFurnitureRoom(FurnitureType furnitureRoom) {
		this.furnitureRoom = furnitureRoom;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
