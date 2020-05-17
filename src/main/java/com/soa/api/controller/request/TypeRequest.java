package com.soa.api.controller.request;

import javax.validation.constraints.NotNull;

public class TypeRequest {

	private Integer id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String acronym;
	
	private int furnitureTypeId;

	public TypeRequest() {
		super();
	}

	public TypeRequest(Integer id, @NotNull String name, @NotNull String acronym) {
		super();
		this.id = id;
		this.name = name;
		this.acronym = acronym;
	}

	public TypeRequest(@NotNull String name, @NotNull String acronym) {
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

	public int getFurnitureTypeId() {
		return furnitureTypeId;
	}

	public void setFurnitureTypeId(int furnitureTypeId) {
		this.furnitureTypeId = furnitureTypeId;
	}
	
}
