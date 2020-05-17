package com.soa.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product_type", uniqueConstraints={
		   @UniqueConstraint(columnNames={"name", "furniture_type_id"}),
		   @UniqueConstraint(columnNames={"acronym"})
		})
public class ProductType {

	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotNull
	@Size(max = 10)
	@Column(name = "acronym", nullable = false)
	private String acronym;
	
	@ManyToOne()
    @JoinColumn(name="furniture_type_id", nullable = false) 
	private FurnitureType furnitureType;
	
	@OneToMany(mappedBy = "productType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Product> products = new ArrayList<Product>();

	public ProductType() {
		super();
	}

	public ProductType(String name, String acronym) {
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

	@JsonIgnore
	public FurnitureType getFurnitureType() {
		return furnitureType;
	}

	public void setFurnitureType(FurnitureType furnitureType) {
		this.furnitureType = furnitureType;
	}
	
}
