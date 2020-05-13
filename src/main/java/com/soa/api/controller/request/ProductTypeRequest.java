package com.soa.api.controller.request;

import javax.validation.constraints.NotNull;

public class ProductTypeRequest {

private Integer id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String acronym;

	public ProductTypeRequest() {
		super();
	}

	public ProductTypeRequest(Integer id, @NotNull String name, @NotNull String acronym) {
		super();
		this.id = id;
		this.name = name;
		this.acronym = acronym;
	}

	public ProductTypeRequest(@NotNull String name, @NotNull String acronym) {
		super();
		this.name = name;
		this.acronym = acronym;
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

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	
}
