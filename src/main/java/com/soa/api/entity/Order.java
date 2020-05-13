package com.soa.api.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_bill")
public class Order {

	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	@NotNull
	@Column(name = "payment_method", nullable = false, columnDefinition="tinyint(1) default 0")
	private Boolean paymentMethod;
	
	@Basic(optional = false)
	@Column(name = "date_create", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreate;
	
	@NotNull
	@Column(name = "total_price", nullable = false, columnDefinition="Decimal(10,3) default '00.000'")
	private double totalPrice;
	
	@ManyToOne()
    @JoinColumn(name="status_id", nullable = false) 
	private Status status;
	
	@ManyToOne()
    @JoinColumn(name="account_id", nullable = false) 
	private Account account;
	
	@OneToMany(targetEntity=OrderDetail.class, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();

	public Order() {
		super();
	}

	public Order(Boolean paymentMethod, double totalPrice) {
		super();
		this.paymentMethod = paymentMethod;
		this.totalPrice = totalPrice;
	}

	public Order(@NotNull Boolean paymentMethod, Date dateCreate, @NotNull double totalPrice, Status status) {
		super();
		this.paymentMethod = paymentMethod;
		this.dateCreate = dateCreate;
		this.totalPrice = totalPrice;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Boolean paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
