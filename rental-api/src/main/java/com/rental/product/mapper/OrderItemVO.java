package com.rental.product.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private long orderItemId;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private OrderVO order;
    private ProductVO product;
}
