package com.rental.product.security;

import java.util.Collection;

import com.rental.product.mapper.UserVO;
import com.rental.product.model.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomUserDetails extends UserEntity implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(UserVO user) {
		this.username = !StringUtils.isBlank(user.getUserName()) ? user.getUserName() : user.getEmail();
		this.password = user.getPassword();

		this.authorities = user.getRoles().stream()
		         .flatMap(role -> role.getPermissions().stream())
		         .map(permission -> 
		                 new SimpleGrantedAuthority("ROLE_" + permission.getName()))
		         .collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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
