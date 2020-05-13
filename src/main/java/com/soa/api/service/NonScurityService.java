package com.soa.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soa.api.entity.Account;
import com.soa.api.entity.FurnitureType;
import com.soa.api.entity.Product;
import com.soa.api.entity.ProductType;
import com.soa.api.entity.ShoppingCart;
import com.soa.api.repository.AccountRepository;
import com.soa.api.repository.BrandRepository;
import com.soa.api.repository.FurnitureTypeRepository;
import com.soa.api.repository.ProductRepository;
import com.soa.api.repository.ProductTypeRepository;
import com.soa.api.repository.ShoppingCartRepository;
import com.soa.api.repository.UserRepository;

@Service
public class NonScurityService {
	
	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	@Autowired
	private FurnitureTypeRepository furnitureTypeRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	public List<ProductType> findAllProductType() {
		return productTypeRepository.findAll();
	}
	
	public List<FurnitureType> findAllFurnitureType() {
		return furnitureTypeRepository.findAll();
	}

	public List<Product> findAllProduct() {
		return productRepository.findAll();
	}

	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}
	
	public List<Product> findProductsByProductAcronym(String acronym) {
		
		ProductType productType = productTypeRepository.findByAcronym(acronym);
		
		if(productType != null) {
			return productRepository.findByProductType(productType);
		} else {
			return null;
		}
	}

	public Product findByProductId(int id) {
		
		Optional<Product> bOptional = productRepository.findById(id);
		
		return bOptional.isPresent() ? bOptional.get() : null;
	}

	public List<ShoppingCart> findAllShoppingCartByUser(String userInfo) {
		
		Account account = accountRepository.findByUserName(userInfo);
		
		return shoppingCartRepository.findByShoppingCartKeyAccountId(account.getId());
	}

	public List<Product> findProductsByProductAcronym(String acronym, String acronymProduct) {
		FurnitureType furnitureType = this.findFurnitureTypeByAcronym(acronym);
		
		List<Product> products = this.findProductsByProductAcronym(acronymProduct);
		
		return products;
	}

	public FurnitureType findFurnitureTypeByAcronym(String acronym) {
		FurnitureType furnitureType = furnitureTypeRepository.findByAcronym(acronym);
		return furnitureType;
	}

	
}
