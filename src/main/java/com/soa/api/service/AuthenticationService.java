package com.soa.api.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soa.api.authentication.JwtTokenProvider;
import com.soa.api.authentication.UserDetailsImp;
import com.soa.api.controller.request.LoginRequest;
import com.soa.api.controller.response.JwtAuthenticationResponse;
import com.soa.api.repository.AccountRepository;

@Service
public class AuthenticationService {
	
	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    JwtTokenProvider tokenProvider;
    
    public ResponseEntity<JwtAuthenticationResponse> userSigin(LoginRequest loginRequest, HttpServletRequest request) {
    	
    	Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken((UserDetailsImp) authentication.getPrincipal());
        
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    
    private boolean checkUserAccount(String username) {
    	boolean checkUserAccount = accountRepository.existsByUserName(username);
    	return checkUserAccount;
    }

}
