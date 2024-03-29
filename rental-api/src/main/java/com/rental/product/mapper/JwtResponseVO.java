package com.rental.product.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private String accessToken;
    private String token;
    private Instant expiredTime;
    private UserVO userVO;
}
