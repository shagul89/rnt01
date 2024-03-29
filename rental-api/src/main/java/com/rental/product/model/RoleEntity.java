package com.rental.product.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.rental.product.enumeration.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "RNT_ROLE")
public class RoleEntity extends AuditorEntity implements Serializable {

	private static final long serialVersionUID = 1234567L;

	@Id
	@Column(name = "ROLE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long roleId;

	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE_NAME")
	private UserType name;

	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE }, mappedBy = "roles")
	@JsonIgnore
	private Set<UserEntity> users = new HashSet<UserEntity>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinTable(name = "RNT_ROLES_PERMISSION", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PERMISSION_ID") })
	private Set<PermissionEntity> permissions = new HashSet<PermissionEntity>();

}
