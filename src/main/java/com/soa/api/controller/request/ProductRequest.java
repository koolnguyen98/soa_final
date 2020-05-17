package com.soa.api.controller.request;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.soa.api.entity.ProductType;
import com.soa.api.entity.FurnitureType;;

public class ProductRequest {

	private int id;

	@NotNull
	@Size(max = 50)
	private String productName;

	@NotNull
	@Size(max = 100)
	private String size;

	@NotNull
	@Size(max = 2000)
	private String description;
	
	@NotNull
	@Size(max = 2000)
	private String material;

	private double discount;

	private double price;

	private boolean status;

	private int amount;

	private int productType;
	
	private List<ProductType> listProductTypes;
	
	private List<FurnitureType> listFurnitureTypes;

	private List<String> imageFiles;

	private List<String> base64Images;

	private int brand;
	
	private List<Integer> color;

	public ProductRequest() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public List<String> getImageFiles() {
		return imageFiles;
	}

	public void setImageFiles(List<String> imageFiles) {
		this.imageFiles = imageFiles;
	}

	public List<String> getBase64Images() {
		return base64Images;
	}

	public void setBase64Images(List<String> base64Images) {
		this.base64Images = base64Images;
	}

	public int getBrand() {
		return brand;
	}

	public void setBrand(int brand) {
		this.brand = brand;
	}

	public List<Integer> getColor() {
		return color;
	}

	public void setColor(List<Integer> color) {
		this.color = color;
	}

	public List<ProductType> getListProductTypes() {
		return listProductTypes;
	}

	public void setListProductTypes(List<ProductType> listProductTypes) {
		this.listProductTypes = listProductTypes;
	}

	public List<FurnitureType> getListFurnitureTypes() {
		return listFurnitureTypes;
	}

	public void setListFurnitureTypes(List<FurnitureType> listFurnitureTypes) {
		this.listFurnitureTypes = listFurnitureTypes;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	
}
