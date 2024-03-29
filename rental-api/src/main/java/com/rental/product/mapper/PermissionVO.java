package com.rental.product.mapper;

import com.rental.product.enumeration.PermissionType;

import com.rental.product.model.RoleEntity;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionVO implements Serializable {

	private static final long serialVersionUID = 1234567L;

	private Long permissionId;
	private String name;
	private PermissionType permissionType;
	private String description;
	private Set<RoleEntity> roles = new HashSet<>();
}
