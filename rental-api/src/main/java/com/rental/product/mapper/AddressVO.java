package com.rental.product.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private long addressId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String addressLine1;
    private String addressLine2;
    private String country;
    private Set<UserVO> users = new HashSet<>();
    private Set<SupplierVO> supplier = new HashSet<>();
    private Set<CustomerVO> customerBillingAddress = new HashSet<>();
    private Set<CustomerVO> customerShippingAddress = new HashSet<>();
    private Set<StoreVO> store = new HashSet<>();
    private String mobileNumber;
    private String officeNumber;
    private String alternateMobileNumber;
}
