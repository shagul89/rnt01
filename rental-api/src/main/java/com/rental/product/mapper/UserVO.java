package com.rental.product.mapper;

import com.rental.product.enumeration.UserStatus;
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
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private UserStatus status;
    private String password;
    private String userName;
    private UserType userType;
    private Set<RoleVO> roles = new HashSet<>();
    private Set<AddressVO> userAddress = new HashSet<>();
    private String createdBy;
    private Date createdDate;
    private String updatedName;
    private Date updatedDate;
    private Long activeStore;

}
