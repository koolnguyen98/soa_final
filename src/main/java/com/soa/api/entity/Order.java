package com.soa.api.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	
	@NotNull
	@Column(name = "date_create")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreate;
	
	@NotNull
	@Column(name = "address", nullable = false)
	private String address;

	@NotNull
	@Column(name = "total_price", nullable = false, columnDefinition="Decimal(10,3) default '00.000'")
	private double totalPrice;
	
	@Enumerated(EnumType.STRING)
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}
