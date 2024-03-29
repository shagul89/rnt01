package com.rental.product.services;

import com.rental.product.mapper.UserVO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {

    Map<String, Object> findAllUser();

    Optional<UserVO> findByUserId(Long userId);

    Optional<UserVO> findByUserEmail(String email);

    String validateUserDetails(UserVO userVO);

    Map<String, Object> saveUser(UserVO userVO);

    String deleteUser(Long userId);

    boolean isExistUser(String email);

}
