package com.rental.product.services;

import com.rental.product.mapper.ProductVO;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    List<ProductVO> findAllProduct();

    Optional<ProductVO> findByProductId(Long productId);

    Optional<ProductVO> findByProductName(String name);

    String validateProductDetails(ProductVO productVO);

    ProductVO saveProduct(ProductVO productVO);

    String deleteProduct(Long productId);
}
