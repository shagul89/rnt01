package com.rental.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rental.product.enumeration.PermissionType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "RNT_PERMISSION")
public class PermissionEntity implements Serializable {

	private static final long serialVersionUID = 1234567L;

	@Id
	@Column(name = "PERMISSION_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long permissionId;
	
	@Column(name = "PERMISSION_NAME")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "PERMISSION_TYPE")
	private PermissionType permissionType;

	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE }, mappedBy = "permissions")
	@JsonIgnore
	private Set<RoleEntity> roles = new HashSet<>();
}
