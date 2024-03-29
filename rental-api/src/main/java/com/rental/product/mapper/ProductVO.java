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
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private long productId;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String typeOfProduct;
    private int warrantyPeriod;
    private Set<StoreVO> store = new HashSet<>();
    private Set<SupplierVO> supplier = new HashSet<>();
    private Set<OrderItemVO> orderItem = new HashSet<>();
    private String createdBy;
    private Date createdDate;
    private String updatedName;
    private Date updatedDate;
}
