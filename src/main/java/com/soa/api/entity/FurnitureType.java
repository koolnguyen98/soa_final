package com.soa.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "furniture_type", uniqueConstraints={
		   @UniqueConstraint(columnNames={"furniture_name"}),
		   @UniqueConstraint(columnNames={"acronym"})
		})
public class FurnitureType {
	
	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "furniture_name", nullable = false)
	private String furnitureName;
	
	@NotNull
	@Size(max = 10)
	@Column(name = "acronym", nullable = false)
	private String acronym;
	
	@OneToMany(mappedBy = "furnitureType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ProductType> productTypes = new ArrayList<ProductType>();

	public FurnitureType() {
		super();
	}

	public FurnitureType(String furnitureName, String acronym) {
		super();
		this.furnitureName = furnitureName;
		this.acronym = acronym;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFurnitureName() {
		return furnitureName;
	}

	public void setFurnitureName(String furnitureName) {
		this.furnitureName = furnitureName;
	}

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public List<ProductType> getProductTypes() {
		return productTypes;
	}

	public void setProductTypes(List<ProductType> productTypes) {
		this.productTypes = productTypes;
	}

}
