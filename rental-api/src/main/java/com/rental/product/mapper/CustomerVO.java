package com.rental.product.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private long customerId;
    private String name;
    private String emailId;
    private Set<OrderVO> order;
    private Set<AddressVO> billingAddress = new HashSet<>();
    private Set<AddressVO> shippingAddress = new HashSet<>();
    private String createdBy;
    private Date createdDate;
    private String updatedName;
    private Date updatedDate;
}
