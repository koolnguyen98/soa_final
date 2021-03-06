package com.soa.api.authentication;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.soa.api.entity.Account;
import com.soa.api.entity.Role;;

public class UserDetailsImp implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3970499916283530433L;
	Account account;
	List<GrantedAuthority> authorities;
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }
	
	 public void setAuthorities(List<GrantedAuthority> authorities)
	    {
	        this.authorities=authorities;
	    }
	
	public UserDetailsImp( ) {
		super();
	}

    public UserDetailsImp(Account account) {
		super();
		this.account = account;
	}

	public Account getUser() {
		return account;
	}

	public void setUser(Account account) {
		this.account = account;
	}

	

	@Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
