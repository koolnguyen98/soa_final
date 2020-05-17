package com.soa.api.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.soa.api.controller.request.BrandRequest;
import com.soa.api.controller.request.LoginRequest;
import com.soa.api.controller.request.ProductRequest;
import com.soa.api.controller.request.TypeRequest;
import com.soa.api.entity.Account;
import com.soa.api.entity.Brand;
import com.soa.api.entity.FurnitureType;
import com.soa.api.entity.Order;
import com.soa.api.entity.Product;
import com.soa.api.entity.ProductType;
import com.soa.api.entity.Role;
import com.soa.api.entity.Status;
import com.soa.api.entity.User;
import com.soa.api.repository.AccountRepository;
import com.soa.api.repository.BrandRepository;
import com.soa.api.repository.FurnitureTypeRepository;
import com.soa.api.repository.OrderRepository;
import com.soa.api.repository.ProductTypeRepository;
import com.soa.api.repository.RoleRepository;
import com.soa.api.repository.UserRepository;

@Service
public class ScurityService {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	@Autowired
	private FurnitureTypeRepository furnitureTypeRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<Role> findAllRole() {
		return roleRepository.findAll();
	}
	
	/**
	 * 
	 *
	 * Product service
	 * 
	 * Add, update, delete product (Book and Stationery), product type, brand, author and publishing company
	 * 
	 * 
	 **/

	public ProductRequest findProductById(int id) {
		return productService.findProductById(id);
	}

	public ResponseEntity<?> insertProduct(ProductRequest productRequest) {
		return productService.insertProduct(productRequest);
	}

	public ResponseEntity<?> updateProduct(ProductRequest productRequest) {
		return productService.updateProduct(productRequest);
	}

	public ResponseEntity<?> deleteProductById(int id) {
		return productService.deleteProductById(id);
	}

	public ResponseEntity<?> findProductTypeById(int id) {
		return productService.findTypeById(id, false);
	}

	public ResponseEntity<?> insertProductType(TypeRequest typeRequest) {
		return productService.insertType(typeRequest, false);
	}

	public ResponseEntity<?> updateProductType(TypeRequest typeRequest) {
		return productService.updateType(typeRequest, false);
	}

	public ResponseEntity<?> deleteProductTypeById(int id) {
		return productService.deleteTypeById(id, false);
	}
	
	public ResponseEntity<?> findFurnitureTypeById(int id) {
		return productService.findTypeById(id,true);
	}

	public ResponseEntity<?> insertFurnitureType(TypeRequest typeRequest) {
		return productService.insertType(typeRequest, true);
	}

	public ResponseEntity<?> updateFurnitureType(TypeRequest typeRequest) {
		return productService.updateType(typeRequest, true);

	}

	public ResponseEntity<?> deleteFurnitureTypeById(int id) {
		return productService.deleteTypeById(id, true);
	}


	public ResponseEntity<?> insertBrand(BrandRequest brandRequest) {
		return productService.insertBrandProperties(brandRequest);
	}

	public ResponseEntity<?> findBrandById(int id) {
		return productService.findBrandById(id);
	}

	public ResponseEntity<?> updateBrand(BrandRequest brandRequest) {
		return productService.updateBrand(brandRequest);
	}

	public ResponseEntity<?> deleteBrandById(int id) {
		return productService.deleteBrandById(id);
	}

	public User findUserByUserName(String userName) {
		
		Account account = accountRepository.findByUserName(userName);
		
		return userRepository.findByAccount(account);
	}
	
	public List<Order> findAllOrderByStatus(String status) {
		Status st = null;

		if (status.equals("CXN")) {
			st = Status.CXN;
		} else if (status.equals("DXN")) {
			st = Status.DXN;
		} else if (status.equals("GTC")) {
			st = Status.GTC;
		} else if (status.equals("GTB")) {
			st = Status.GTB;
		}

		List<Order> orders = orderRepository.findByStatus(st);
		return orders;
	}

	public boolean confirmOrder(int id) {
		Optional<Order> orOptional = orderRepository.findById(id);

		Order order = orOptional.isPresent() ? orOptional.get() : null;

		if (order == null) {
			return false;
		}

		order.setStatus(Status.DXN);

		orderRepository.save(order);

		return true;
	}

	public Order findOrderById(int id) {
		Optional<Order> orOptional = orderRepository.findById(id);

		return orOptional.isPresent() ? orOptional.get() : null;
	}

	public boolean confirmOrder(int id, boolean status) {
		Optional<Order> orOptional = orderRepository.findById(id);

		Order order = orOptional.isPresent() ? orOptional.get() : null;

		if (order == null) {
			return false;
		}

		Status st = null;
		
		if(status) {
			st = Status.GTC;
		} else {
			st = Status.GTB;
		}

		order.setStatus(st);

		orderRepository.save(order);

		return true;
	}

}
