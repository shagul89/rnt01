package com.rental.product.controller;

import com.rental.product.constant.RentalConstant;
import com.rental.product.mapper.ResponseVO;
import com.rental.product.mapper.ProductVO;
import com.rental.product.services.IProductService;
import com.rental.product.util.AppUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "RCT Product Service", description = "Product Service API")
@RestController
@RequestMapping("/rct/product")
public class ProductController {

    private final AppUtil appUtil;

    private final IProductService productService;

    @Autowired
    public ProductController(AppUtil appUtil, IProductService productService) {
        this.appUtil = appUtil;
        this.productService = productService;
    }

    @Operation(summary = "Get All Product Service", description = "Find All Product Data", tags = {"RCT Get All Product"})
    @GetMapping(value = "/get-all-product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getAll(){
        return new ResponseEntity<>(appUtil.successResponse(productService.findAllProduct(), RentalConstant.RESPONSE_DATA, "Get All Product details successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Get Product By Id Service", description = "Find Product By Id Data", tags = {"RCT Get Product By Id"})
    @GetMapping(value = "/get-by-product-id/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getByProductId(@PathVariable Long productId){
        Optional<ProductVO> ProductVO = productService.findByProductId(productId);
        return ProductVO.map(vo -> new ResponseEntity<>(appUtil.successResponse(vo, RentalConstant.RESPONSE_DATA, "Get Product By Id details successfully"), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(appUtil.failedResponse("No Data Found", "Product Data Not Found in Data base"), HttpStatus.BAD_REQUEST));
    }

    @Operation(summary = "Save Product Service", description = "Save Product Data", tags = {"RCT Save Product"})
    @PostMapping(value = "/save-product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveProduct(@RequestBody ProductVO productVO){
        Optional<ProductVO> ProductVODb = productService.findByProductName(productVO.getName());
        if(ProductVODb.isEmpty()){
            String error = productService.validateProductDetails(productVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(productService.saveProduct(productVO), RentalConstant.RESPONSE_DATA,"Product data created successfully"), HttpStatus.CREATED);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Product email already exists %s in data base", productVO.getName())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Save Product Service", description = "Save Product Data", tags = {"RCT Save Product"})
    @PutMapping(value = "/update-product/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveProduct(@PathVariable Long productId, @RequestBody ProductVO productVO){
        Optional<ProductVO> productVODb = productService.findByProductName(productVO.getName());
        if(productVODb.isPresent()){
            String error = productService.validateProductDetails(productVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(productService.saveProduct(productVO), RentalConstant.RESPONSE_DATA,"Product data updated successfully"), HttpStatus.NO_CONTENT);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Product name %s not found in data base", productVO.getName())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete Product Service", description = "Delete Product Data", tags = {"RCT Delete Product"})
    @DeleteMapping(value = "/delete-product-by-id/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> deleteProduct(@PathVariable Long productId){
        Optional<ProductVO> productVODB = productService.findByProductId(productId);
        if(productVODB.isPresent()){
            String response = productService.deleteProduct(productId);
            return new ResponseEntity<>(appUtil.successResponse(response, RentalConstant.RESPONSE_DATA, response), HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Product Id not found %s in data base", productId)), HttpStatus.CREATED);
        }
    }
}
