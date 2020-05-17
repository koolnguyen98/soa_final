package com.soa.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soa.api.controller.request.RegisterRequest;
import com.soa.api.controller.response.ApiResponse;
import com.soa.api.entity.Account;
import com.soa.api.entity.Role;
import com.soa.api.entity.User;
import com.soa.api.repository.AccountRepository;
import com.soa.api.repository.RoleRepository;
import com.soa.api.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AuthService authService;

	public ResponseEntity<?> createUser(RedirectAttributes redirect, RegisterRequest registerRequest) {
		String email = registerRequest.getEmail();
		String username = registerRequest.getUsername();

		User existedUser = userRepository.findByEmail(email);

		if (existedUser != null) {
			
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Email has already taken");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
			
		}

		Account existedAccount = accountRepository.findByUserName(username);

		if (existedAccount != null) {
			
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Username has already taken");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}

		User user = new User();

		user.setAddress(registerRequest.getAddress());
		user.setEmail(registerRequest.getEmail());
		user.setFullName(registerRequest.getFullName());
		user.setPhoneNumber(registerRequest.getPhoneNumber());
		user.setSex(registerRequest.isSex());

		User entity = userRepository.save(user);

		if (entity == null) {

			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Create user failed");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}

		Role userRole = roleRepository.findByRole("USER");

		List<Role> roles = new ArrayList<Role>();
		roles.add(userRole != null ? userRole : null);

		Account account = new Account();

		account.setUserName(registerRequest.getUsername());
		account.setPassword(authService.hashpwd(registerRequest.getPassword()));
		account.setUser(entity);
		account.setRoles(roles);

		Account addedAccount = accountRepository.save(account);

		if (addedAccount == null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Create user failed");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
			
		}
		
		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setSuccess(true);
		
		apiResponse.setMessage("Create account successfully");
		
		apiResponse.setObject(null);
		
		return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
		
	}

	public ResponseEntity<?> userProfile(Model model, String username, RedirectAttributes redirect) {
		RegisterRequest registerRequest = null;
		try {
		Account account = accountRepository.findByUserName(username);

		if (account == null) {
			
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Username is not found");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
		
		User user = userRepository.findUserByAccountId(account.getId());

		if (user == null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Username is not found");
			
			apiResponse.setObject(null);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
		

		registerRequest = new RegisterRequest((Long) user.getId(), user.getFullName(), user.getEmail(),
				user.getPhoneNumber(), user.getAddress(), user.isSex());

		
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setSuccess(true);
		
		apiResponse.setMessage("Successfully");
		
		apiResponse.setObject(registerRequest);
		
		return new ResponseEntity<Object>(apiResponse, HttpStatus.OK);
	}
	
	public ResponseEntity<?> updateUserProfile(RedirectAttributes redirect, RegisterRequest registerRequest) {
		String email = registerRequest.getEmail();

		User existedUser = userRepository.findByEmail(email);

		if (existedUser != null && existedUser.getId() != registerRequest.getId()) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Email has already taken");
			
			apiResponse.setObject(registerRequest);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}


		User user = new User();

		user.setId(registerRequest.getId());
		user.setAddress(registerRequest.getAddress());
		user.setEmail(registerRequest.getEmail());
		user.setFullName(registerRequest.getFullName());
		user.setPhoneNumber(registerRequest.getPhoneNumber());
		user.setSex(registerRequest.isSex());

		User entity = userRepository.save(user);

		if (entity == null) {
			ApiResponse apiResponse = new ApiResponse();
			
			apiResponse.setSuccess(false);
			
			apiResponse.setMessage("Update user profile faile");
			
			apiResponse.setObject(registerRequest);
			
			return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
		}
		
		ApiResponse apiResponse = new ApiResponse();
		
		apiResponse.setSuccess(true);
		
		apiResponse.setMessage("Update account successfully");
		
		apiResponse.setObject(registerRequest);
		
		return new ResponseEntity<Object>(apiResponse, HttpStatus.NOT_FOUND);
	}
}
