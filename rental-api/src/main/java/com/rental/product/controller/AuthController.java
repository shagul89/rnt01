package com.rental.product.controller;

import com.rental.product.mapper.AuthRequestVO;
import com.rental.product.mapper.JwtResponseVO;
import com.rental.product.mapper.RefreshTokenRequestVO;
import com.rental.product.mapper.UserVO;
import com.rental.product.model.RefreshTokenEntity;
import com.rental.product.services.IRefreshTokenService;
import com.rental.product.services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.rental.product.security.JwtService;

import java.time.Instant;
import java.util.Optional;

@Tag(name = "RNT Auth Service", description = "Auth Service API")
@RestController
@RequestMapping("/rnt/auth")
public class AuthController {

	private final JwtService jwtService;

	private final AuthenticationManager authenticationManager;

	private final IUserService userService;

	private final IRefreshTokenService refreshTokenService;

	@Autowired
    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager, IUserService userService, IRefreshTokenService refreshTokenService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }


	@Operation(summary = "RNT Get the Access Token", description = "Get The Access Token", tags = {"RNT Get Access Token"})
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public JwtResponseVO login(@RequestBody AuthRequestVO authRequestVO){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestVO.getUsername(), authRequestVO.getPassword()));
		if(authentication.isAuthenticated()){
			Optional<UserVO> user = userService.findByUserEmail(authRequestVO.getUsername());
			if(user.isPresent()){
				RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user.get().getEmail());
				return JwtResponseVO.builder()
						.accessToken(jwtService.GenerateToken(authRequestVO.getUsername()))
						.token(refreshToken.getToken()).expiredTime(Instant.now().plusMillis(600000)).build();
			} else{
				throw new IllegalArgumentException("Invalid username or password.");
			}
		} else {
			throw new UsernameNotFoundException("invalid user request..!!");
		}
	}

	@PostMapping("/refreshToken")
	public JwtResponseVO refreshToken(@RequestBody RefreshTokenRequestVO refreshTokenRequestVO){
		return refreshTokenService.findByToken(refreshTokenRequestVO.getToken())
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshTokenEntity::getUserName)
				.map(userInfo -> {
					String accessToken = jwtService.GenerateToken(userInfo);
					return JwtResponseVO.builder()
							.accessToken(accessToken)
							.token(refreshTokenRequestVO.getToken()).userVO(userService.findByUserEmail(userInfo).get()).build();
				}).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
	}


}
