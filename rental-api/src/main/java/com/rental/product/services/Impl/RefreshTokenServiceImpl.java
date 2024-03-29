package com.rental.product.services.Impl;

import com.rental.product.model.RefreshTokenEntity;
import com.rental.product.repository.RefreshTokenRepository;
import com.rental.product.repository.UserRepository;
import com.rental.product.services.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements IRefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshTokenEntity createRefreshToken(String username){
        Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenRepository.findByUserName(username);
        if(refreshTokenEntity.isPresent()){
            return refreshTokenEntity.get();
        } else{
            RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                    .userName(username)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(600000))
                    .build();
            return refreshTokenRepository.save(refreshToken);
        }
    }


    @Override
    public Optional<RefreshTokenEntity> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;

    }
}
