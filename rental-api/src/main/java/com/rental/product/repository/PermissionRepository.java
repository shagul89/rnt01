package com.rental.product.repository;

import com.rental.product.enumeration.PermissionType;
import com.rental.product.mapper.PermissionVO;
import com.rental.product.model.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    List<PermissionEntity> findByName(String name);
}
