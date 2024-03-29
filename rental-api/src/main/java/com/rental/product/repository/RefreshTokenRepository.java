package com.rental.product.repository;

import com.rental.product.model.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    @Query("SELECT T FROM RefreshTokenEntity T WHERE T.token = ?1")
    Optional<RefreshTokenEntity> findByToken(String token);

    @Query("SELECT T FROM RefreshTokenEntity T WHERE T.userName = ?1")
    Optional<RefreshTokenEntity> findByUserName(String userName);
}
