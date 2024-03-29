package com.rental.product.services;

import com.rental.product.mapper.StoreVO;

import java.util.List;
import java.util.Optional;

public interface IStoreService {
    List<StoreVO> findAllStore();

    Optional<StoreVO> findByStoreId(Long storeId);

    Optional<StoreVO> findByStoreName(String name);

    String validateStoreDetails(StoreVO storeVO);

    StoreVO saveStore(StoreVO storeVO);

    String deleteStore(Long storeId);
}
