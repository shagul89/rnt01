package com.rental.product.services.Impl;

import com.rental.product.exception.RentalException;
import com.rental.product.mapper.CustomerVO;
import com.rental.product.mapper.ProductVO;
import com.rental.product.model.CustomerEntity;
import com.rental.product.model.ProductEntity;
import com.rental.product.repository.ProductRepository;
import com.rental.product.services.IProductService;
import com.rental.product.util.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductVO> findAllProduct() {
        List<ProductVO> productVOList = new ArrayList<>();
        try{
            List<ProductEntity> productEntityList = productRepository.findAll();
            BeanUtils.copyProperties(productEntityList, productVOList);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return productVOList;
    }

    @Override
    public Optional<ProductVO> findByProductId(Long productId) {
        ProductVO productVO = new ProductVO();
        try{
            Optional<ProductEntity> productEntity = productRepository.findById(productId);
            if(productEntity.isPresent()){
                BeanUtils.copyProperties(productEntity.get(), productVO);
                return Optional.of(productVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProductVO> findByProductName(String name) {
        ProductVO productVO = new ProductVO();
        try{
            Optional<ProductEntity> productEntity = productRepository.findByName(name);
            if(productEntity.isPresent()){
                BeanUtils.copyProperties(productEntity.get(), productVO);
                return Optional.of(productVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public String validateProductDetails(ProductVO productVO) {
        AtomicReference<String> error = new AtomicReference<>("");
        try{
            if(StringUtils.isBlank(productVO.getName())){
                error.set(error + " Product Name");
            }
            if(StringUtils.isBlank(productVO.getDescription())){
                error.set(error + " Product Specification");
            }
            if(productVO.getPrice() == 0){
                error.set(error + " Product Price");
            }
            if(productVO.getPrice() == 0){
                error.set(error + " Product Price");
            }
            if(productVO.getQuantity() == 0){
                error.set(error + " Product Quantity");
            }
            if(StringUtils.isBlank(productVO.getTypeOfProduct())){
                error.set(error + " Type Of Product");
            }
            if(productVO.getStore().isEmpty()){
                error.set(error + " Store Of Product");
            }else{
                productVO.getStore().forEach(e -> {
                    if(StringUtils.isBlank(e.getName())){
                        error.set(error + " Store Name");
                    }
                    if(e.getStoreAddress().isEmpty()){
                        error.set(error + " Store Address");
                    }else {
                        e.getStoreAddress().forEach(a -> {
                            error.set(AppUtil.validateAddress(a, "Store "));
                        });
                    }
                });
            }

            if(productVO.getSupplier().isEmpty()){
                error.set(error + " Supplier Of Product");
            }else{
                productVO.getSupplier().forEach(e -> {
                    if(StringUtils.isBlank(e.getName())){
                        error.set(error + " Supplier Name");
                    }
                    if(e.getSupplierAddress().isEmpty()){
                        error.set(error + " Supplier Address");
                    }else {
                        e.getSupplierAddress().forEach(a -> {
                            error.set(AppUtil.validateAddress(a, "Supplier "));
                        });
                    }
                });
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return error.get();
    }

    @Override
    public ProductVO saveProduct(ProductVO productVO) {
        try{
            ProductEntity productEntity = new ProductEntity();
            BeanUtils.copyProperties(productVO, productEntity);
            productEntity = productRepository.save(productEntity);
            BeanUtils.copyProperties(productEntity, productVO);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return productVO;
    }

    @Override
    public String deleteProduct(Long productId) {
        try{
            productRepository.deleteById(productId);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return "Product deleted successfully";
    }
}
