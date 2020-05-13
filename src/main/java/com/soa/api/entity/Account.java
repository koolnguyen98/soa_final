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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "account", uniqueConstraints={
		   @UniqueConstraint(columnNames={"user_name"})
		})
public class Account {
	
	@Id
    @GeneratedValue
    @Column(name = "id")
	private Integer id;
	
	@NotNull
	@Size(max = 50)
	@Column(name = "user_name", nullable = false)
	private String userName;
	
	@NotNull
	@Size(max = 100)
	@Column(name = "password", nullable = false)
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
	  name = "role_account", 
	  joinColumns = @JoinColumn(name = "account_id"), 
	  inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;
	
	@Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
	private boolean enabled;
	
	@OneToMany(targetEntity=ShoppingCart.class, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ShoppingCart> shoppingCarts = new ArrayList<ShoppingCart>();

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Order> orders = new ArrayList<Order>();
	
	public Account() {
		super();
	}

	public Account(String userName, String password, User user) {
		super();
		this.userName = userName;
		this.password = password;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}

	@JsonIgnore
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	public List<ShoppingCart> getShoppingCarts() {
		return shoppingCarts;
	}

	public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
		this.shoppingCarts = shoppingCarts;
	}

	@JsonIgnore
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
