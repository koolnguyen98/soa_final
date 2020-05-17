package com.soa.api.controller;

import java.net.URI;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.dom4j.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soa.api.Urls;
import com.soa.api.controller.request.BrandRequest;
import com.soa.api.controller.request.ProductRequest;
import com.soa.api.controller.request.TypeRequest;
import com.soa.api.controller.response.ApiResponse;
import com.soa.api.controller.response.ResponseBill;
import com.soa.api.controller.response.ShopResponse;
import com.soa.api.entity.FurnitureType;
import com.soa.api.entity.Order;
import com.soa.api.entity.Product;
import com.soa.api.entity.ProductType;
import com.soa.api.entity.ShoppingCart;
import com.soa.api.service.NonScurityService;
import com.soa.api.service.ScurityService;

@RestController
public class AdminController {

	@Autowired
	private ScurityService scurityService;
	
	@Autowired
	private NonScurityService nonScurityService;

	/**
	 * 
	 * 
	 * Book Product 
	 * 
	 * 
	 **/

	@RequestMapping(value = Urls.API_ADMIN_ADD_PRODUCT, method = RequestMethod.POST)
	public ResponseEntity<?> addBook(@RequestBody @Valid ProductRequest productRequest, Principal principal) {
		productRequest.setStatus(true);
		return scurityService.insertProduct(productRequest);
	}

	@RequestMapping(value = Urls.API_ADMIN_UPDATE_PRODUCT, method = RequestMethod.GET)
	public ResponseEntity<?> updateBookPage(@PathVariable int id) {

		ProductRequest productRequest = scurityService.findProductById(id);

		if (productRequest != null) {
			
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(true);
			
			apiResponse.setMessage("Successfully");
			
			apiResponse.setObject(productRequest);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
	
		}
		
		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setSuccess(false);
		
		apiResponse.setMessage("Product doesn't exist");
		
		apiResponse.setObject(null);
		
		return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = Urls.API_ADMIN_UPDATE_PRODUCT, method = RequestMethod.POST)
	public ResponseEntity<?> updateBook(Model model, @RequestBody @Valid ProductRequest productRequest, @PathVariable int id) {
		productRequest.setId(id);
		return scurityService.updateProduct(productRequest);

	}
	
	@RequestMapping(value = Urls.API_ADMIN_DELETE_PRODUCT, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> deleteBook(@PathVariable int id) {

		return scurityService.deleteProductById(id);
		
	}
	
	@RequestMapping(value = Urls.API_ADMIN_PRODUCT, method = RequestMethod.GET)
	public ResponseEntity<?> allBook(Model model, Principal principal) {

		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setSuccess(true);
		
		apiResponse.setMessage("Successfully");
		
		apiResponse.setObject(nonScurityService.findAllProducts());
		
		return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		
	}
	
	/**
	 * 
	 *
	 *  Product type and Furniture Type
	 * 
	 * 
	 **/
	
	@RequestMapping(value = Urls.API_ADMIN_PRODUCT_TYPE, method = RequestMethod.GET)
	public ResponseEntity<?> findAllProductType() {

		return ResponseEntity.ok(nonScurityService.findAllProductType());
		
	}

	@RequestMapping(value = Urls.API_ADMIN_ADD_PRODUCT_TYPE, method = RequestMethod.POST)
	public ResponseEntity<?> addProductType(@RequestBody @Valid TypeRequest typeRequest) {

		return scurityService.insertProductType(typeRequest);

	}

	@RequestMapping(value = Urls.API_ADMIN_UPDATE_PRODUCT_TYPE, method = RequestMethod.GET)
	public ResponseEntity<?> updateProductTypePage(@PathVariable int id) {

		return scurityService.findProductTypeById(id);

	}
	
	@RequestMapping(value = Urls.API_ADMIN_UPDATE_PRODUCT_TYPE, method = RequestMethod.POST)
	public ResponseEntity<?> updateProductType(@ModelAttribute("productTypeForm") @Valid TypeRequest typeRequest, @PathVariable int id) {
		typeRequest.setId(id);
		return scurityService.updateProductType(typeRequest);

	}
	
	@RequestMapping(value = Urls.API_ADMIN_DELETE_PRODUCT_TYPE, method = RequestMethod.POST)
	public ResponseEntity<?> deleteProductType(@PathVariable int id) {

		return scurityService.deleteProductTypeById(id);
		
	}
	
	@RequestMapping(value = Urls.API_ADMIN_FURNITURE_TYPE, method = RequestMethod.GET)
	public ResponseEntity<?> findAllFurnitureType() {

		return ResponseEntity.ok(nonScurityService.findAllFurnitureType());
		
	}
	
	@RequestMapping(value = Urls.API_ADMIN_ADD_FURNITURE_TYPE, method = RequestMethod.POST)
	public ResponseEntity<?> addFurnitureType(@RequestBody @Valid TypeRequest typeRequest) {

		return scurityService.insertFurnitureType(typeRequest);

	}

	@RequestMapping(value = Urls.API_ADMIN_UPDATE_FURNITURE_TYPE, method = RequestMethod.GET)
	public ResponseEntity<?> updateFurnitureTypePage(@PathVariable int id) {
		
		return scurityService.findFurnitureTypeById(id);

	}
	
	@RequestMapping(value = Urls.API_ADMIN_UPDATE_FURNITURE_TYPE, method = RequestMethod.POST)
	public ResponseEntity<?> updateFurnitureType(@ModelAttribute("productTypeForm") @Valid TypeRequest typeRequest, @PathVariable int id) {
		typeRequest.setId(id);
		return scurityService.updateFurnitureType(typeRequest);

	}
	
	@RequestMapping(value = Urls.API_ADMIN_DELETE_FURNITURE_TYPE, method = RequestMethod.POST)
	public ResponseEntity<?> deleteFurnitureType(@PathVariable int id) {

		return scurityService.deleteFurnitureTypeById(id);
		
	}
	
	/**
	 * 
	 *
	 *  Additional Properties
	 * 
	 * 
	 **/
	
	@RequestMapping(value = Urls.API_ADMIN_BRAND, method = RequestMethod.GET)
	public ResponseEntity<?> findAllBrand() {

		return ResponseEntity.ok(nonScurityService.findAllBrand());
	}

	@RequestMapping(value = Urls.API_ADMIN_ADD_BRAND, method = RequestMethod.POST)
	public ResponseEntity<?> addBrand(@RequestBody @Valid BrandRequest brandRequest) {

		return scurityService.insertBrand(brandRequest);

	}

	@RequestMapping(value = Urls.API_ADMIN_UPDATE_BRAND, method = RequestMethod.GET)
	public ResponseEntity<?> updateBrandPage(Model model, @PathVariable("id") int id) {

		return scurityService.findBrandById(id);

	}
	
	@RequestMapping(value = Urls.API_ADMIN_UPDATE_BRAND, method = RequestMethod.POST)
	public ResponseEntity<?> updateAdditional(@RequestBody @Valid BrandRequest brandRequest) {

		return scurityService.updateBrand(brandRequest);

	}
	
	@RequestMapping(value = Urls.API_ADMIN_DELETE_BRAND, method = RequestMethod.POST)
	public ResponseEntity<?> deleteBrand(@PathVariable int id) {

		return scurityService.deleteBrandById(id);
		
	}
	
	/**
	 * 
	 *
	 *  Bill manage
	 * 
	 * 
	 **/
	
	@RequestMapping(value = Urls.API_ADMIN_BILL, method = RequestMethod.GET)
	public ResponseEntity<?> findAllBillByStatus(Principal principal) {

		ResponseBill responseBill = new ResponseBill();
		
		List<Order> way = scurityService.findAllOrderByStatus("CXN");
		responseBill.setWaitConfirm(way);
		
		List<Order> confirm = scurityService.findAllOrderByStatus("DXN");
		responseBill.setConfirm(confirm);
		
		List<Order> success = scurityService.findAllOrderByStatus("GTC");
		responseBill.setSuccess(success);
		
		List<Order> unsuccess = scurityService.findAllOrderByStatus("GTB");
		responseBill.setUnsuccess(unsuccess);

		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setSuccess(true);
		
		apiResponse.setMessage("Success");
		
		apiResponse.setObject(responseBill);
		
		return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
	}
	
	@RequestMapping(value = Urls.API_ADMIN_CONFIRM_BILL, method = RequestMethod.POST)
	public ResponseEntity<?> confirmOrder(Principal principal, @PathVariable int id) {
		
		boolean confirm = scurityService.confirmOrder(id);
		
		if (confirm) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(true);
			
			apiResponse.setMessage("Success");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		} else {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Failed");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	@RequestMapping(value = Urls.API_ADMIN_BILL_ID, method = RequestMethod.GET)
	public ResponseEntity<?> showOrder(Principal principal, @PathVariable int id) {

		
		Order order = scurityService.findOrderById(id);
		
		
		if (order != null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(true);
			
			apiResponse.setMessage("Success");
			
			apiResponse.setObject(order);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		} else {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Failed");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = Urls.API_ADMIN_BILL_DELIVERY, method = RequestMethod.POST)
	public ResponseEntity<?> diliveryOrder(Principal principal, @PathVariable int id, @RequestParam("success") boolean status) {
		
		boolean confirm = scurityService.confirmOrder(id, status);
		
		if (confirm) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(true);
			
			if(status) {
				apiResponse.setMessage("Delivery Success");
			} else {
				apiResponse.setMessage("Delivery Unsuccess");
			}
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		} else {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Failed");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

}
