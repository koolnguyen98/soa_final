package com.soa.api.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soa.api.entity.Account;
import com.soa.api.entity.Role;
import com.soa.api.repository.AccountRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		// Check user
		Account account = accountRepository.findByUserName(username);
		if (account == null) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
 
        List<Role> roles = account.getRoles();
 
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roles != null) {
            for (Role role : roles) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
                grantList.add(authority);
            }
        }
 
        UserDetails userDetails = (UserDetails) new User(account.getUserName(), //
        		account.getPassword(), grantList);
 
        return userDetails;
	}

	// This method is used by JWTAuthenticationFilter
	@Transactional
	public UserDetails loadUserById(int id) {
		// Check user
				Optional<Account> account = accountRepository.findById(id);
				if (!account.isPresent()) {
					throw new UsernameNotFoundException("User not found with id : " + id);
				}
				
				return new UserDetailsImp(account.get());
	}
}
