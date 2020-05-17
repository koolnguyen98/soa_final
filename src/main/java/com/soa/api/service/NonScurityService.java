package com.soa.api.service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.soa.api.authentication.UserDetailsImp;
import com.soa.api.controller.response.ShopResponse;
import com.soa.api.entity.Account;
import com.soa.api.entity.Brand;
import com.soa.api.entity.FurnitureType;
import com.soa.api.entity.Product;
import com.soa.api.entity.ProductType;
import com.soa.api.entity.Role;
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
	
	public List<Brand> findAllBrand() {
		return brandRepository.findAll();
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

	public List<Product> findProductByName(String textSearch) {
		List<Product> products = productRepository.findByProductNameSearch(textSearch);
		return products;
	}
	
	public  ShopResponse generateShopResponse(Principal principal) {
		String userInfo = "";
		List<ShoppingCart> shoppingCarts = null;
		boolean admin = false;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		         UserDetailsImp loginedUser = (UserDetailsImp) auth.getPrincipal();
			userInfo = loginedUser.getUsername();
		
			admin = loginedUser.getAuthorities().stream()
			        	.anyMatch(r -> r.getAuthority().equals("ADMIN"));

			shoppingCarts = findAllShoppingCartByUser(userInfo);
		}

		ShopResponse response = new ShopResponse();

		response.setAdmin(admin);

		response.setUserName(userInfo);

		response.setShoppingCarts(shoppingCarts);

		return response;
	}
}
