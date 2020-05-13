package com.soa.api.controller.request;

import javax.validation.constraints.NotNull;

public class BrandRequest {

	private Integer id;

	@NotNull
	private String name;

	@NotNull
	private String country;

	public BrandRequest() {
		super();
	}

	public BrandRequest(@NotNull String name, @NotNull String country) {
		super();
		this.name = name;
		this.country = country;
	}

	public BrandRequest(Integer id, @NotNull String name, @NotNull String country) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
