package com.soa.api.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.soa.api.Urls;
import com.soa.api.authentication.UserDetailsImp;
import com.soa.api.controller.request.LoginRequest;
import com.soa.api.controller.response.ApiResponse;
import com.soa.api.controller.response.ShopResponse;
import com.soa.api.entity.Account;
import com.soa.api.entity.FurnitureType;
import com.soa.api.entity.Product;
import com.soa.api.entity.Role;
import com.soa.api.entity.ShoppingCart;
import com.soa.api.service.AuthenticationService;
import com.soa.api.service.NonScurityService;
import com.soa.api.service.ScurityService;

@RestController
public class ShoppingController {

	@Autowired
	private ScurityService scurityService;

	@Autowired
	private NonScurityService nonScurityService;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping(value = Urls.API_AUTH)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return authenticationService.userSigin(loginRequest);
	}

	@GetMapping(value = Urls.API_SHOP)
	public ResponseEntity<?> shopView(Principal principal) {

		List<FurnitureType> furnitureTypes = nonScurityService.findAllFurnitureType();

		ShopResponse response = this.generateShopResponse(principal);

		response.setFurnitureTypes(furnitureTypes);
		
		return ResponseEntity.ok(response);
		
	}

	@GetMapping(value = Urls.API_SHOP_FURNITURE)
	public ResponseEntity<?> furitureView(Principal principal, @PathVariable("acronym") String acronym) {

		List<FurnitureType> furnitureTypes = nonScurityService.findAllFurnitureType();

		FurnitureType furnitureRoom = nonScurityService.findFurnitureTypeByAcronym(acronym);

		ShopResponse response = this.generateShopResponse(principal);

		response.setFurnitureTypes(furnitureTypes);
		
		response.setFurnitureRoom(furnitureRoom);
		
		if(furnitureRoom == null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Loại phòng không tồn tại!");
			
			apiResponse.setObject(response);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = Urls.API_SHOP_FURNITURE_PRODUCT)
	public ResponseEntity<?> furitureProductView(Principal principal, @PathVariable("acronym") String acronym,
			@PathVariable("acronymProdcut") String acronymProdcut) {

		List<FurnitureType> furnitureTypes = nonScurityService.findAllFurnitureType();

		FurnitureType furnitureRoom = nonScurityService.findFurnitureTypeByAcronym(acronym);

		List<Product> products = nonScurityService.findProductsByProductAcronym(acronym, acronymProdcut);

		ShopResponse response = this.generateShopResponse(principal);

		response.setFurnitureTypes(furnitureTypes);

		response.setFurnitureRoom(furnitureRoom);
		
		if(furnitureRoom == null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Loại phòng không tồn tại!");
			
			apiResponse.setObject(response);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
		
		response.setProducts(products);
		
		if(products == null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Loại sản phẩm không tồn tại!");
			
			apiResponse.setObject(response);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = Urls.API_SHOP_FURNITURE_PRODUCT_ID)
	public ResponseEntity<?> furitureProductView(Principal principal, @PathVariable("acronym") String acronym,
			@PathVariable("acronymProdcut") String acronymProdcut, @PathVariable("id") int id) {

		List<FurnitureType> furnitureTypes = nonScurityService.findAllFurnitureType();

		FurnitureType furnitureRoom = nonScurityService.findFurnitureTypeByAcronym(acronym);

		List<Product> products = nonScurityService.findProductsByProductAcronym(acronym, acronymProdcut);

		Product product = nonScurityService.findByProductId(id);

		ShopResponse response = this.generateShopResponse(principal);

		response.setFurnitureTypes(furnitureTypes);

		response.setFurnitureRoom(furnitureRoom);
		
		if(furnitureRoom == null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Loại phòng không tồn tại!");
			
			apiResponse.setObject(response);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}

		response.setProducts(products);
		
		if(products == null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Loại sản phẩm không tồn tại!");
			
			apiResponse.setObject(response);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}

		response.setProduct(product);
		
		if(product == null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Sản phẩm không tồn tại!");
			
			apiResponse.setObject(response);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(response);
	}

	private ShopResponse generateShopResponse(Principal principal) {
		String userInfo = "";
		List<ShoppingCart> shoppingCarts = null;
		boolean admin = false;
		if (principal != null) {
			UserDetailsImp loginedUser = (UserDetailsImp) ((Authentication) principal).getPrincipal();
			userInfo = loginedUser.getUsername();

			Role role = loginedUser.getRole();
		
			admin = role.getRole().equals("ADMIN") ? true : false;

			shoppingCarts = nonScurityService.findAllShoppingCartByUser(userInfo);
		}

		ShopResponse response = new ShopResponse();

		response.setAdmin(admin);

		response.setUserName(userInfo);

		response.setShoppingCarts(shoppingCarts);

		return response;
	}

}
