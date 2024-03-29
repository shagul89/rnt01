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
public class OrderVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private long orderId;
    private Date orderDate;
    private double totalAmount;
    private String status;
    private CustomerVO customer;
    private Set<OrderItemVO> orderItem = new HashSet<>();
    private String createdBy;
    private Date createdDate;
    private String updatedName;
    private Date updatedDate;
}
