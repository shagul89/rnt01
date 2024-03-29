package com.rental.product.repository;

import com.rental.product.model.ConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Long> {
    Optional<ConfigurationEntity> findByName(String name);
}
