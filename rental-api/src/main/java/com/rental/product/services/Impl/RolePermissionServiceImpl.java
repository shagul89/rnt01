package com.rental.product.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.product.constant.RentalConstant;
import com.rental.product.exception.RentalException;
import com.rental.product.mapper.PermissionVO;
import com.rental.product.mapper.RoleVO;
import com.rental.product.model.PermissionEntity;
import com.rental.product.model.RoleEntity;
import com.rental.product.repository.PermissionRepository;
import com.rental.product.repository.RoleRepository;
import com.rental.product.services.IRolePermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RolePermissionServiceImpl implements IRolePermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public RolePermissionServiceImpl(PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Map<String, Object> saveRolePermission(List<RoleVO> roleVOList) {
        Map<String, Object> map = new HashMap<>();
        AtomicReference<Set<PermissionEntity>> permissionEntitiesList = new AtomicReference<>(new HashSet<>());
        ObjectMapper mapper = new ObjectMapper();
        try{
            List<RoleEntity> roleEntityListDb = roleRepository.findAll();
            roleVOList.forEach(roleVO -> {
                permissionEntitiesList.set(new HashSet<>());
                roleVO.getPermissions().forEach(e -> {
                    Optional<PermissionEntity> isExists = permissionRepository.findByName(e.getName()).stream().filter(p -> p.getPermissionType().name().equalsIgnoreCase(e.getPermissionType().name())).findFirst();
                    if(isExists.isPresent()){
                        permissionEntitiesList.get().add(isExists.get());
                    }else{
                        String error = validatePermissionDetails(e);
                        if(!StringUtils.isBlank(error)){
                            map.put("permissionError", error);
                        }else{
                            PermissionEntity permissionEntity = mapper.convertValue(e, PermissionEntity.class);
                            permissionEntity = permissionRepository.save(permissionEntity);
                            permissionEntitiesList.get().add(permissionEntity);
                        }
                    }
                });

                if(!map.containsKey("permissionError")){
                    if(roleVO.getPermissions().isEmpty()){
                        map.put("roleError", "Permission Not Found");
                    }else{
                        if(roleVO.getName() == null){
                            map.put("roleError", "Role Name Not Found");
                        }else{
                            Optional<RoleEntity> roleEntityDb = roleEntityListDb.stream().filter(e -> e.getName().name().equalsIgnoreCase(roleVO.getName().name())).findFirst();
                            if(roleEntityDb.isPresent()){
                                boolean permissionFlag = permissionEntitiesList.get().stream().anyMatch(e -> e.getPermissionId() == 0);
                                if(permissionFlag){
                                    roleEntityDb.get().setPermissions(permissionEntitiesList.get());
                                    roleRepository.save(roleEntityDb.get());
                                }
                            } else{
                                RoleEntity roleEntity = mapper.convertValue(roleVO, RoleEntity.class);
                                roleEntity.setPermissions(permissionEntitiesList.get());
                                roleRepository.save(roleEntity);
                            }
                        }
                    }
                }
            });
            map.put("success", "All Roles Save or Updates Successfully");

        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return map;
    }

    @Override
    public Map<String, Object> findAllRoles() {
        Map<String, Object> map = new HashMap<>();
        try{
            map.put(RentalConstant.LIST_VO, roleRepository.findAll());
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return map;
    }

    public String validatePermissionDetails(PermissionVO permissionVO) {
        AtomicReference<String> error = new AtomicReference<>("");
        try{
            if(StringUtils.isBlank(permissionVO.getName())){
                error.set(error + " Permission Name");
            }
            if(permissionVO.getPermissionType() ==null && StringUtils.isBlank(permissionVO.getPermissionType().name())){
                error.set(error + " Permission Type");
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return error.get();
    }
}
