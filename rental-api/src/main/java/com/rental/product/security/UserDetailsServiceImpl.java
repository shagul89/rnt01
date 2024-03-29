package com.rental.product.security;

import com.rental.product.mapper.UserVO;
import com.rental.product.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserVO> user = userService.findByUserEmail(username);
        if(user.isPresent()){
            logger.info("User Authenticated Successfully..!!!");
            user.get().setUserName(username);
            return new CustomUserDetails(user.get());
        }else{
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }

    }

}
