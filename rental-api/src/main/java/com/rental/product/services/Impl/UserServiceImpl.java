package com.rental.product.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.product.constant.RentalConstant;
import com.rental.product.enumeration.UserType;
import com.rental.product.exception.RentalException;
import com.rental.product.mapper.UserVO;
import com.rental.product.model.AddressEntity;
import com.rental.product.model.RoleEntity;
import com.rental.product.model.UserEntity;
import com.rental.product.repository.AddressRepository;
import com.rental.product.repository.RoleRepository;
import com.rental.product.repository.UserRepository;
import com.rental.product.services.IUserService;
import com.rental.product.util.AppUtil;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Transactional
@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Map<String, Object> findAllUser() {
        Map<String, Object> map = new HashMap<>();
        try{
            map.put(RentalConstant.LIST_VO, userRepository.findAll());
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return map;
    }

    @Override
    public Optional<UserVO> findByUserId(Long userId) {
        ObjectMapper ow = new ObjectMapper();
        try{
            Optional<UserEntity> userEntity = userRepository.findById(userId);
            if(userEntity.isPresent()){
                String json = ow.writeValueAsString(userEntity.get());
                UserVO userVO = ow.readValue(json, UserVO.class);
                return Optional.of(userVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserVO> findByUserEmail(String email) {
        ObjectMapper ow = new ObjectMapper();
        try{
            Optional<UserEntity> userEntity = userRepository.findByEmail(email);
            if(userEntity.isPresent()){
                String json = ow.writeValueAsString(userEntity.get());
                UserVO userVO = ow.readValue(json, UserVO.class);
                return Optional.of(userVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public boolean isExistUser(String email) {
        try{
            Optional<UserEntity> userEntity = userRepository.findByEmail(email);
            if(userEntity.isPresent()){
                return true;
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return false;
    }

    @Override
    public String validateUserDetails(UserVO userVO) {
        String error = "";
        try{
            if(StringUtils.isBlank(userVO.getEmail())){
                error = error + " User Email";
            }
            if(StringUtils.isBlank(userVO.getFirstName())){
                error = error + " User First Name";
            }
            if(StringUtils.isBlank(userVO.getLastName())){
                error = error + " User Last Name";
            }
            if(userVO.getUserType().isEmpty()){
                error = error + " User Type";
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return error;
    }

    @Override
    public Map<String, Object> saveUser(UserVO userVO) {
        Map<String, Object> map = new HashMap<>();
        Set<AddressEntity> addressEntities = new HashSet<>();
        Set<RoleEntity> roleEntities = new HashSet<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            userVO.getUserAddress().forEach(e -> {
                String error = AppUtil.validateAddress(e, "User ");
                if(!StringUtils.isBlank(error)){
                    map.put("userAddressError", error);
                }else {
                    AddressEntity addressEntity = mapper.convertValue(e, AddressEntity.class);
                    addressEntity = addressRepository.save(addressEntity);
                    addressEntities.add(addressEntity);
                }
            });

            boolean isSuperAdmin = userVO.getUserType().stream().anyMatch(e -> e.equalsIgnoreCase(UserType.SUPER_ADMIN.name()));
            if(isSuperAdmin){
                roleEntities.addAll(roleRepository.findAll());
            } else {
                for(String userType : userVO.getUserType()){
                    roleEntities.addAll(roleRepository.findAll().stream().filter(e -> e.getName().name().equalsIgnoreCase(userType)).toList());
                }
            }

            UserEntity userEntity = mapper.convertValue(userVO, UserEntity.class);
            userEntity.setRoles(roleEntities);
            userEntity.setUserAddress(addressEntities);
            userEntity = userRepository.save(userEntity);
            userVO.setUserId(userEntity.getUserId());
            map.put("success", userVO);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return map;
    }

    @Override
    public String deleteUser(Long userId) {
        try{
            userRepository.deleteById(userId);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return "User deleted successfully";
    }
}
