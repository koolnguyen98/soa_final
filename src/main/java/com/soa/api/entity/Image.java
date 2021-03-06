package com.soa.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "image")
public class Image {

	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	@NotNull
	@Column(name = "image", nullable = false)
	private String image;
	
	@ManyToOne()
    @JoinColumn(name="product_id", nullable = false) 
	private Product product;

	public Image() {
		super();
	}

	public void setImage(String image) {
		this.image = image;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getImage() {
		return image;
	}
	
	@JsonIgnore
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
