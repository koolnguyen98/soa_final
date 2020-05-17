package com.soa.api.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soa.api.Urls;
import com.soa.api.authentication.UserDetailsImp;
import com.soa.api.controller.request.LoginRequest;
import com.soa.api.controller.request.RegisterRequest;
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
import com.soa.api.service.UserService;

@RestController
public class ShoppingController {

	@Autowired
	private ScurityService scurityService;

	@Autowired
	private NonScurityService nonScurityService;

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private UserService userService;

	@PostMapping(value = Urls.API_AUTH)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
		return authenticationService.userSigin(loginRequest, request);
	}

	@GetMapping(value = Urls.API_SHOP)
	public ResponseEntity<?> shopView(Principal principal) {

		List<FurnitureType> furnitureTypes = nonScurityService.findAllFurnitureType();

		ShopResponse response = nonScurityService.generateShopResponse(principal);

		response.setFurnitureTypes(furnitureTypes);
		
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping(value = Urls.API_SHOP_SEARCH)
	public ResponseEntity<?> searchBook(Model model, Principal principal, @RequestParam(value="textSearch") String textSearch) {
		
		List<FurnitureType> furnitureTypes = nonScurityService.findAllFurnitureType();

		ShopResponse response = nonScurityService.generateShopResponse(principal);
		
		response.setFurnitureTypes(furnitureTypes);
		
		List<Product> products = nonScurityService.findProductByName(textSearch);
		
		response.setProducts(products);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = Urls.API_SHOP_FURNITURE)
	public ResponseEntity<?> furitureView(Principal principal, @PathVariable("acronym") String acronym) {

		List<FurnitureType> furnitureTypes = nonScurityService.findAllFurnitureType();

		FurnitureType furnitureRoom = nonScurityService.findFurnitureTypeByAcronym(acronym);

		ShopResponse response = nonScurityService.generateShopResponse(principal);

		response.setFurnitureTypes(furnitureTypes);
		
		response.setFurnitureRoom(furnitureRoom);
		
		if(furnitureRoom == null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Furniture does not exist!");
			
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

		ShopResponse response = nonScurityService.generateShopResponse(principal);

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

	@GetMapping(value = {Urls.API_SHOP_FURNITURE_PRODUCT_ID, Urls.API_SHOP_FURNITURE_ID, Urls.API_SHOP_ID})
	public ResponseEntity<?> furitureProductView(Principal principal, @PathVariable("acronym") String acronym,
			@PathVariable("acronymProdcut") String acronymProdcut, @PathVariable("id") int id) {

		List<FurnitureType> furnitureTypes = nonScurityService.findAllFurnitureType();

		FurnitureType furnitureRoom = nonScurityService.findFurnitureTypeByAcronym(acronym);

		List<Product> products = nonScurityService.findProductsByProductAcronym(acronym, acronymProdcut);

		Product product = nonScurityService.findByProductId(id);

		ShopResponse response = nonScurityService.generateShopResponse(principal);

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
	
	@RequestMapping(value = "api/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(RedirectAttributes redirectAttrs, @RequestBody @Validated RegisterRequest registerRequest) {
		
		return userService.createUser(redirectAttrs, registerRequest);
	}
	
	@RequestMapping(value = "api/user/profile", method = RequestMethod.GET)
	public ResponseEntity<?> userProfile(Model model, Principal principal, RedirectAttributes redirect) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		         UserDetailsImp userDetailsImp = (UserDetailsImp) auth.getPrincipal();
		         return userService.userProfile(model, userDetailsImp.getUsername(), redirect);
		}
		
		
		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setSuccess(false);
		
		apiResponse.setMessage("Vui lòng đăng nhập!");
		
		apiResponse.setObject(null);
		
		return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "api/user/profile/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateUserProfile(Principal principal, RedirectAttributes redirect, @RequestBody @Validated RegisterRequest registerRequest) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
		         UserDetailsImp userDetailsImp = (UserDetailsImp) auth.getPrincipal();
		         return userService.updateUserProfile(redirect, registerRequest);
		}
		
		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setSuccess(false);
		
		apiResponse.setMessage("Vui lòng đăng nhập!");
		
		apiResponse.setObject(null);
		
		return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
	}

}
