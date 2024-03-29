package com.rental.product.controller;

import com.rental.product.constant.RentalConstant;
import com.rental.product.mapper.SupplierVO;
import com.rental.product.mapper.ResponseVO;
import com.rental.product.services.ISupplierService;
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

@Tag(name = "RCT Supplier Service", description = "Supplier Service API")
@RestController
@RequestMapping("/rct/supplier")
public class SupplierController {

    private final ISupplierService supplierService;

    private final AppUtil appUtil;

    @Autowired
    public SupplierController(ISupplierService supplierService, AppUtil appUtil) {
        this.supplierService = supplierService;
        this.appUtil = appUtil;
    }

    @Operation(summary = "Get All Supplier Service", description = "Find All Supplier Data", tags = {"RCT Get All Supplier"})
    @GetMapping(value = "/get-all-supplier", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getAll(){
        return new ResponseEntity<>(appUtil.successResponse(supplierService.findAllSupplier(), RentalConstant.RESPONSE_DATA, "Get All Supplier details successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Get Supplier By Id Service", description = "Find Supplier By Id Data", tags = {"RCT Get Supplier By Id"})
    @GetMapping(value = "/get-by-supplier-id/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getBySupplierId(@PathVariable Long supplierId){
        Optional<SupplierVO> SupplierVO = supplierService.findBySupplierId(supplierId);
        return SupplierVO.map(vo -> new ResponseEntity<>(appUtil.successResponse(vo, RentalConstant.RESPONSE_DATA, "Get Supplier By Id details successfully"), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(appUtil.failedResponse("No Data Found", "Supplier Data Not Found in Data base"), HttpStatus.BAD_REQUEST));
    }

    @Operation(summary = "Save Supplier Service", description = "Save Supplier Data", tags = {"RCT Save Supplier"})
    @PostMapping(value = "/save-supplier", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveSupplier(@RequestBody SupplierVO supplierVO){
        Optional<SupplierVO> SupplierVODb = supplierService.findBySupplierEmail(supplierVO.getEmail());
        if(SupplierVODb.isEmpty()){
            String error = supplierService.validateSupplierDetails(supplierVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(supplierService.saveSupplier(supplierVO), RentalConstant.RESPONSE_DATA,"Supplier data created successfully"), HttpStatus.CREATED);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Supplier email already exists %s in data base", supplierVO.getEmail())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Save Supplier Service", description = "Save Supplier Data", tags = {"RCT Save Supplier"})
    @PutMapping(value = "/update-supplier/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveSupplier(@PathVariable Long supplierId, @RequestBody SupplierVO supplierVO){
        Optional<SupplierVO> supplierVODb = supplierService.findBySupplierEmail(supplierVO.getEmail());
        if(supplierVODb.isPresent()){
            String error = supplierService.validateSupplierDetails(supplierVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(supplierService.saveSupplier(supplierVO), RentalConstant.RESPONSE_DATA,"Supplier data updated successfully"), HttpStatus.NO_CONTENT);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Supplier email %s not found in data base", supplierVO.getEmail())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete Supplier Service", description = "Delete Supplier Data", tags = {"RCT Delete Supplier"})
    @DeleteMapping(value = "/delete-supplier-by-id/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> deleteSupplier(@PathVariable Long supplierId){
        Optional<SupplierVO> SupplierVODB = supplierService.findBySupplierId(supplierId);
        if(SupplierVODB.isPresent()){
            String response = supplierService.deleteSupplier(supplierId);
            return new ResponseEntity<>(appUtil.successResponse(response, RentalConstant.RESPONSE_DATA, response), HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Supplier Id not found %s in data base", supplierId)), HttpStatus.CREATED);
        }
    }
    
}
