package com.rental.product.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequestVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private String token;
}
