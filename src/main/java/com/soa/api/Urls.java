package com.soa.api;

public interface Urls {

	public final String API = "/api";
	
	public final String API_AUTH = "/api/auth";
	
	public final String API_SCURITY = API + "/scurity";
	
	public final String API_GUEST = API + "/guest";
	
	public final String API_SHOP = API + "/shop";
	
	public final String API_SHOP_SEARCH = API_SHOP + "/product/search";
	
	public final String API_SHOP_FURNITURE = API_SHOP + "/{acronym}";

	public final String API_SHOP_FURNITURE_PRODUCT = API_SHOP_FURNITURE + "/{acronymProdcut}";
	
	public final String API_SHOP_FURNITURE_PRODUCT_ID = API_SHOP_FURNITURE_PRODUCT + "/{id}";
	
	public final String API_SHOP_FURNITURE_ID = API_SHOP_FURNITURE + "product/{id}";
	
	public final String API_SHOP_ID = API_SHOP + "product/{id}";
	
	public final String API_ADMIN = API + "/admin";
	
	public final String API_ADMIN_PRODUCT = API_ADMIN + "/product";
	
	public final String API_ADMIN_ADD_PRODUCT = API_ADMIN + "/addProduct";
	
	public final String API_ADMIN_UPDATE_PRODUCT = API_ADMIN + "/editProduct/{id}";
	
	public final String API_ADMIN_DELETE_PRODUCT = API_ADMIN + "/deleteProduct/{id}";
	
	public final String API_ADMIN_PRODUCT_TYPE = API_ADMIN + "/productType";
	
	public final String API_ADMIN_ADD_PRODUCT_TYPE = API_ADMIN + "/addProductType";
	
	public final String API_ADMIN_GET_PRODUCT_UPDATE_PRODUCT_TYPE = API_ADMIN + "/editProductType/{id}";
	
	public final String API_ADMIN_UPDATE_PRODUCT_TYPE = API_ADMIN + "/editProductType";
	
	public final String API_ADMIN_DELETE_PRODUCT_TYPE = API_ADMIN + "/deleteProductType/{id}";
	
	public final String API_ADMIN_FURNITURE_TYPE = API_ADMIN + "/furnitureType";
	
	public final String API_ADMIN_ADD_FURNITURE_TYPE = API_ADMIN + "/addFurnitureType";
	
	public final String API_ADMIN_UPDATE_FURNITURE_TYPE = API_ADMIN + "/editFurnitureType/{id}";
	
	public final String API_ADMIN_DELETE_FURNITURE_TYPE = API_ADMIN + "/deleteFurnitureType/{id}";
	
	public final String API_ADMIN_BRAND = API_ADMIN + "/brand";
	
	public final String API_ADMIN_ADD_BRAND = API_ADMIN + "/addBrand";
	
	public final String API_ADMIN_UPDATE_BRAND = API_ADMIN + "/editBrand/{id}";
	
	public final String API_ADMIN_DELETE_BRAND = API_ADMIN + "/deleteBrand/{id}";
	
	public final String API_ADMIN_BILL = API_ADMIN + "/bill";
	
	public final String API_ADMIN_CONFIRM_BILL = API_ADMIN + "/confirmBill";
	
	public final String API_ADMIN_BILL_ID = API_ADMIN_BILL + "/{id}";
	
	public final String API_ADMIN_BILL_DELIVERY = API_ADMIN_BILL + "/delivery/{id}";
	
	public final String API_SHOPPINGCARTS = API + "/shoppingcarts";
	
	public final String API_SHOPPINGCARTS_ADD_PRODUCT = API_SHOPPINGCARTS + "/{id}";
	
	public final String API_SHOPPINGCARTS_UPDATE_PRODUCT = API_SHOPPINGCARTS + "/update/{id}";
	
	public final String API_SHOPPINGCARTS_ADD_NUMBER_PRODUCT = API_SHOPPINGCARTS + "/{id}/addNumberProduct";
	
	public final String API_SHOPPINGCARTS_MINUS_NUMBER_PRODUCT = API_SHOPPINGCARTS + "/{id}/minusNumberProduct";
	
	public final String API_SHOPPINGCARTS_DELETE_PRODUCT = API_SHOPPINGCARTS + "/{id}/delete";
	
	public final String API_SHOPPINGCARTS_ORDER = API_SHOPPINGCARTS + "/order";
	
	public final String API_SHOPPINGCARTS_ORDERING = API_SHOPPINGCARTS + "/ordering";
}
