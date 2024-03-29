package com.rental.product.services;

import com.rental.product.mapper.RoleVO;

import java.util.List;
import java.util.Map;

public interface IRolePermissionService {
    Map<String, Object> saveRolePermission(List<RoleVO> roleVO);

    Map<String, Object> findAllRoles();

}
