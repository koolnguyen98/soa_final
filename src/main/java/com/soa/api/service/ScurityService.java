package com.soa.api.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.soa.api.controller.request.BrandRequest;
import com.soa.api.controller.request.LoginRequest;
import com.soa.api.controller.request.ProductRequest;
import com.soa.api.controller.request.ProductTypeRequest;
import com.soa.api.entity.Account;
import com.soa.api.entity.Brand;
import com.soa.api.entity.Product;
import com.soa.api.entity.ProductType;
import com.soa.api.entity.Role;
import com.soa.api.entity.User;
import com.soa.api.repository.AccountRepository;
import com.soa.api.repository.BrandRepository;
import com.soa.api.repository.FurnitureTypeRepository;
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

	public List<ProductType> findAllProductType() {
		return productTypeRepository.findAll();
	}

	public List<Brand> findAllBrand() {
		return brandRepository.findAll();
	}
	
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

	public Product insertProduct(ProductRequest productRequest) {
		return productService.insertProduct(productRequest);
	}

	public Product updateProduct(ProductRequest productRequest) {
		return productService.updateProduct(productRequest);
	}

	public Boolean deleteProductById(int id) {
		return productService.deleteProductById(id);
	}

	public ProductTypeRequest findProductTypeById(int id) {
		return productService.findProductTypeById(id);
	}

	public ProductType insertProductType(ProductTypeRequest productTypeRequest) {
		return productService.insertProductType(productTypeRequest);
	}

	public ProductType updateProductType(ProductTypeRequest productTypeRequest) {
		return productService.updateProductType(productTypeRequest);
	}

	public Boolean deleteProductTypeById(int id) {
		return productService.deleteProductTypeById(id);
	}

	public Boolean insertAdditionalProperties(BrandRequest brandRequest) {
		return productService.insertBrandProperties(brandRequest);
	}

	public BrandRequest findBrandById(int id) {
		return productService.findBrandById(id);
	}

	public Boolean updateAdditional(BrandRequest brandRequest) {
		return productService.updateBrand(brandRequest);
	}

	public Boolean deleteBrandById(int id) {
		return productService.deleteBrandById(id);
	}

	public User findUserByUserName(String userName) {
		
		Account account = accountRepository.findByUserName(userName);
		
		return userRepository.findByAccount(account);
	}

}
