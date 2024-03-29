package com.rental.product.services;

import com.rental.product.model.RefreshTokenEntity;

import java.util.Optional;

public interface IRefreshTokenService {

    RefreshTokenEntity createRefreshToken(String username);

    Optional<RefreshTokenEntity> findByToken(String token);

    RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);
}
