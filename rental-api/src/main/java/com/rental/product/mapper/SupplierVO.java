package com.rental.product.mapper;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplierVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private long supplierId;
    private String name;
    private String email;
    private Set<ProductVO> products = new HashSet<>();
    private Set<AddressVO> supplierAddress = new HashSet<>();
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedName;
    private LocalDateTime updatedDate;
}
