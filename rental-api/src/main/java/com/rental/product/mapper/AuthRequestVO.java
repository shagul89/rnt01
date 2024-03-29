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
public class AuthRequestVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private String username;
    private String password;
}
