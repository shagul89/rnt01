package com.rental.product.controller;

import com.rental.product.constant.RentalConstant;
import com.rental.product.mapper.ResponseVO;
import com.rental.product.mapper.RoleVO;
import com.rental.product.mapper.UserVO;
import com.rental.product.services.IRolePermissionService;
import com.rental.product.util.AppUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.rental.product.services.IUserService;

import java.util.List;
import java.util.Optional;

@Tag(name = "RNT User Service", description = "User Service API")
@RestController
@RequestMapping("/rnt/user")
public class UserController {

	@Value("${defaultPassword}")
	private String defaultPassword;
	
	private final IUserService userService;
	private final AppUtil appUtil;
	private final IRolePermissionService rolePermissionService;
	
	@Autowired
	public UserController(IUserService userService, AppUtil appUtil, IRolePermissionService rolePermissionService) {
		this.userService = userService;
        this.appUtil = appUtil;
        this.rolePermissionService = rolePermissionService;
    }


	@Operation(summary = "Get All User Service", description = "Find All User Data", tags = {"RNT Get All User"})
	@GetMapping(value = "/get-all-user", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> getAll(){
		return new ResponseEntity<>(appUtil.successResponse(userService.findAllUser(), RentalConstant.RESPONSE_DATA, "Get all user details successfully"), HttpStatus.OK);
	}

	@Operation(summary = "Get User By Id Service", description = "Find User By Id Data", tags = {"RNT Get User By Id"})
	@GetMapping(value = "/get-by-user-id/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> getByUserId(@PathVariable Long userId){
		Optional<UserVO> userVO = userService.findByUserId(userId);
        return userVO.map(vo -> new ResponseEntity<>(appUtil.successResponse(vo, RentalConstant.RESPONSE_DATA, "Get user by id details successfully"), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(appUtil.failedResponse("No Data Found", "User Data Not Found in Data base"), HttpStatus.BAD_REQUEST));
	}

	@Operation(summary = "Save User Service", description = "Save User Data", tags = {"RNT Save User"})
	@PostMapping(value = "/save-user", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> saveUser(@RequestBody UserVO userVO){
		Optional<UserVO> userVODb = userService.findByUserEmail(userVO.getEmail());
		if(userVODb.isEmpty()){
			String error = userService.validateUserDetails(userVO);
			if(!StringUtils.isBlank(error)){
				return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
			}else{
				userVO.setUserName(userVO.getEmail());
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String encodedPassword = encoder.encode(defaultPassword);
				userVO.setPassword(encodedPassword);
				return new ResponseEntity<>(appUtil.successResponse(userService.saveUser(userVO), RentalConstant.RESPONSE_DATA,"User data created successfully"), HttpStatus.CREATED);
			}
		}else{
			return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("User email already exists %s in data base", userVO.getEmail())), HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Update User Service", description = "Update User Data", tags = {"RNT Update User"})
	@PutMapping(value = "/update-user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> updateUser(@PathVariable Long userId, @RequestBody UserVO userVO){
		Optional<UserVO> userVODb = userService.findByUserId(userId);
		if(userVODb.isPresent()){
			String error = userService.validateUserDetails(userVO);
			if(!StringUtils.isBlank(error)){
				return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
			}else{
				userVO.setUserName(userVO.getEmail());
				userVO.setPassword(userVODb.get().getPassword());
				return new ResponseEntity<>(appUtil.successResponse(userService.saveUser(userVO), RentalConstant.RESPONSE_DATA,"User data created successfully"), HttpStatus.NO_CONTENT);
			}
		}else{
			return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("User Id not found %s in data base", userId)), HttpStatus.BAD_REQUEST);
		}
	}

	@Operation(summary = "Delete User Service", description = "Delete User Data", tags = {"RNT Delete User"})
	@DeleteMapping(value = "/delete-user-by-id/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> deleteUser(@PathVariable Long userId){
		Optional<UserVO> userVODb = userService.findByUserId(userId);
		if(userVODb.isPresent()){
			String response = userService.deleteUser(userId);
			return new ResponseEntity<>(appUtil.successResponse(response, RentalConstant.RESPONSE_DATA, response), HttpStatus.NO_CONTENT);
		}else{
			return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("User Id not found %s in data base", userId)), HttpStatus.CREATED);
		}
	}

	@Operation(summary = "Save Role Permission Service", description = "Save Role Permission Data", tags = {"RNT Role Permission User"})
	@PostMapping(value = "/save-role-permission", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> saveRolePermission(@RequestBody List<RoleVO> roleVO){
		return new ResponseEntity<>(appUtil.successResponse(rolePermissionService.saveRolePermission(roleVO), RentalConstant.RESPONSE_DATA,"User data created successfully"), HttpStatus.CREATED);
	}

	@Operation(summary = "Get All Role Permission Service", description = "Find All Role Permission Data", tags = {"RNT Get All Role Permission"})
	@GetMapping(value = "/get-all-roles-permission", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseVO> getAllRolesPermission(){
		return new ResponseEntity<>(appUtil.successResponse(rolePermissionService.findAllRoles(), RentalConstant.RESPONSE_DATA, "Get all role permission details successfully"), HttpStatus.OK);
	}

}
