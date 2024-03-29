package com.rental.product.mapper;


import com.rental.product.enumeration.UserType;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleVO implements Serializable {

	private static final long serialVersionUID = 1234567L;

	private Long roleId;
	private UserType name;
	private String description;
	private Set<UserVO> users = new HashSet<>();
	private Set<PermissionVO> permissions = new HashSet<>();
	private String createdBy;
	private Date createdDate;
	private String updatedName;
	private Date updatedDate;

}
