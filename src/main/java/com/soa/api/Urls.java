package com.soa.api;

public interface Urls {

	public final String API = "/api";
	
	public final String API_AUTH = "/api/auth";
	
	public final String API_SCURITY = API + "/scurity";
	
	public final String API_GUEST = API + "/guest";
	
	public final String API_SHOP = API + "/shop";
	
	public final String API_SHOP_FURNITURE = API_SHOP + "/{acronym}";

	public final String API_SHOP_FURNITURE_PRODUCT = API_SHOP_FURNITURE + "/{acronymProdcut}";
	
	public final String API_SHOP_FURNITURE_PRODUCT_ID = API_SHOP_FURNITURE_PRODUCT + "/{id}";
	
}
