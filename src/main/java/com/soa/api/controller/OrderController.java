package com.soa.api.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.soa.api.controller.request.OrderRequest;
import com.soa.api.controller.request.RegisterRequest;
import com.soa.api.controller.response.ApiResponse;
import com.soa.api.controller.response.ShopResponse;
import com.soa.api.controller.response.ShoppingCartsResponse;
import com.soa.api.entity.Account;
import com.soa.api.entity.FurnitureType;
import com.soa.api.entity.Order;
import com.soa.api.entity.OrderDetail;
import com.soa.api.entity.Product;
import com.soa.api.entity.Role;
import com.soa.api.entity.ShoppingCart;
import com.soa.api.service.AuthenticationService;
import com.soa.api.service.NonScurityService;
import com.soa.api.service.OrderService;
import com.soa.api.service.ScurityService;
import com.soa.api.service.UserService;

@RestController
public class OrderController {

	@Autowired
	private ScurityService scurityService;

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = Urls.API_SHOPPINGCARTS, method = RequestMethod.GET)
	public ResponseEntity<?> findShoppingcartByUser(Principal principal) {

		String userInfo = "";
		List<ShoppingCart> shoppingCarts = null;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetailsImp loginedUser = (UserDetailsImp) auth.getPrincipal();
			userInfo = loginedUser.getUsername();
			shoppingCarts = orderService.findShoppingcartByUsername(userInfo);

			
		}
		double totalPrice = 0.0;
		
		for (ShoppingCart shoppingCart : shoppingCarts) {
			totalPrice += shoppingCart.getTotalPrice();
		}
		
		ShoppingCartsResponse shoppingCartsResponse = new ShoppingCartsResponse();
		
		shoppingCartsResponse.setShoppingCarts(shoppingCarts);
		
		shoppingCartsResponse.setTotalPrice(totalPrice);
		
		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setSuccess(true);
		
		apiResponse.setMessage("Success");
		
		apiResponse.setObject(shoppingCartsResponse);
		
		return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = Urls.API_SHOPPINGCARTS_ORDER, method = RequestMethod.GET)
	public ResponseEntity<?> orderLink(Principal principal) {

		String userInfo = "";
		List<ShoppingCart> shoppingCarts = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetailsImp loginedUser = (UserDetailsImp) auth.getPrincipal();
			userInfo = loginedUser.getUsername();
			
			shoppingCarts = orderService.findShoppingcartByUsername(userInfo);

			
		}
		double totalPrice = 0.0;
		
		for (ShoppingCart shoppingCart : shoppingCarts) {
			totalPrice += shoppingCart.getTotalPrice();
		}
		
		com.soa.api.entity.User user = scurityService.findUserByUserName(userInfo);
		
		ShoppingCartsResponse shoppingCartsResponse = new ShoppingCartsResponse();
		
		shoppingCartsResponse.setShoppingCarts(shoppingCarts);
		
		shoppingCartsResponse.setTotalPrice(totalPrice);
		
		shoppingCartsResponse.setUser(user);
		
		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setSuccess(true);
		
		apiResponse.setMessage("Success");
		
		apiResponse.setObject(shoppingCartsResponse);
		
		return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
	}

	@RequestMapping(value = Urls.API_SHOPPINGCARTS_ADD_PRODUCT, method = RequestMethod.POST)
	public ResponseEntity<?> addProdcutIntoShoppingCart(@PathVariable("id") int id, @RequestParam(value="number") int number) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}

		boolean results = orderService.addProdcutIntoShoppingCart(id, userName, number);

		if (results) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(true);
			
			apiResponse.setMessage("Add product into shoppingcart Successfully");

			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		} else {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Add product into shoppingcart Failed");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = Urls.API_SHOPPINGCARTS_UPDATE_PRODUCT, method = RequestMethod.POST)
	public ResponseEntity<?> updateProdcutIntoShoppingCart(@PathVariable("id") int id, @RequestParam(value="number") int number) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}

		boolean results = orderService.updateAmountProdcutIntoShoppingCart(id, userName, number);

		if (results) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(true);
			
			apiResponse.setMessage("Update product into shoppingcart Successfully");

			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		} else {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Update product into shoppingcart Failed");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = Urls.API_SHOPPINGCARTS_ADD_NUMBER_PRODUCT, method = RequestMethod.POST)
	public ResponseEntity<?> addNumberProdcutIntoShoppingCart(@PathVariable int id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}

		boolean result = orderService.updateProdcutIntoShoppingCart(id, userName, 1);

		if (result) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(true);
			
			apiResponse.setMessage("Update number of product into shoppingcart Successfully");

			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		} else {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Update number of product into shoppingcart Failed");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = Urls.API_SHOPPINGCARTS_MINUS_NUMBER_PRODUCT, method = RequestMethod.POST)
	public ResponseEntity<?> minusNumberProdcutIntoShoppingCart(@PathVariable int id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}

		boolean result = orderService.updateProdcutIntoShoppingCart(id, userName, -1);
		
		List<ShoppingCart> shoppingCarts = orderService.findShoppingcartByUsername(userName);

		if (result) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(true);
			
			apiResponse.setMessage("Update number of product into shoppingcart Successfully");

			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		} else {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Update number of product into shoppingcart Failed");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = Urls.API_SHOPPINGCARTS_DELETE_PRODUCT, method = RequestMethod.POST)
	public ResponseEntity<?> deleteProdcutIntoShoppingCart(@PathVariable int id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}

		boolean result = orderService.deleteProductIntoShoppingCart(id, userName);
		
		if (result) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(true);
			
			apiResponse.setMessage("Delete product of user into shoppingcart Successfully");

			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		} else {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Delete product of user into shoppingcart Failed");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = Urls.API_SHOPPINGCARTS_ORDERING, method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> ordering(@RequestBody @Valid OrderRequest orderRequest) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		
		List<ShoppingCart> shoppingCarts = orderService.findShoppingcartByUsername(userName);
		
		Order order = orderService.ordering(userName, orderRequest);
		
		List<OrderDetail> orderDetails = orderService.findOrderDetailDByOrder(order);

		if (order != null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(true);
			
			apiResponse.setMessage("Order Successfully");

			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		} else {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Order Failed");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

}
