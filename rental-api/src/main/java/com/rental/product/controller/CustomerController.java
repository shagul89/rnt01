package com.rental.product.controller;

import com.rental.product.constant.RentalConstant;
import com.rental.product.mapper.CustomerVO;
import com.rental.product.mapper.ResponseVO;
import com.rental.product.services.ICustomerService;
import com.rental.product.util.AppUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Tag(name = "RNT Customer Service", description = "Customer Service API")
@RestController
@RequestMapping("/rnt/customer")
public class CustomerController {

    private final ICustomerService customerService;

    private final AppUtil appUtil;

    @Autowired
    public CustomerController(ICustomerService customerService, AppUtil appUtil) {
        this.customerService = customerService;
        this.appUtil = appUtil;
    }

    @Operation(summary = "Get All Customer Service", description = "Find All Customer Data", tags = {"RNT Get All Customer"})
    @GetMapping(value = "/get-all-customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getAll(){
        return new ResponseEntity<>(appUtil.successResponse(customerService.findAllCustomer(), RentalConstant.RESPONSE_DATA, "Get All Customer details successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Get Customer By Id Service", description = "Find Customer By Id Data", tags = {"RNT Get Customer By Id"})
    @GetMapping(value = "/get-by-customer-id/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getByCustomerId(@PathVariable Long customerId){
        Optional<Map<String, Object>> customerVO = customerService.findByCustomerId(customerId);
        return customerVO.map(vo -> new ResponseEntity<>(appUtil.successResponse(vo, RentalConstant.RESPONSE_DATA, "Get Customer By Id details successfully"), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(appUtil.failedResponse("No Data Found", "Customer Data Not Found in Data base"), HttpStatus.BAD_REQUEST));
    }

    @Operation(summary = "Save Customer Service", description = "Save Customer Data", tags = {"RNT Save Customer"})
    @PostMapping(value = "/save-customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveCustomer(@RequestBody CustomerVO customerVO){
        Optional<CustomerVO> customerVODb = customerService.findByCustomerEmail(customerVO.getEmailId());
        if(customerVODb.isEmpty()){
            String error = customerService.validateCustomerDetails(customerVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(customerService.saveCustomer(customerVO), RentalConstant.RESPONSE_DATA,"Customer data created successfully"), HttpStatus.CREATED);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Customer email already exists %s in data base", customerVO.getEmailId())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Save Customer Service", description = "Save Customer Data", tags = {"RNT Save Customer"})
    @PutMapping(value = "/update-customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveCustomer(@PathVariable Long customerId, @RequestBody CustomerVO customerVO){
        Optional<CustomerVO> customerVODb = customerService.findByCustomerEmail(customerVO.getEmailId());
        if(customerVODb.isPresent()){
            String error = customerService.validateCustomerDetails(customerVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(customerService.saveCustomer(customerVO), RentalConstant.RESPONSE_DATA,"Customer data updated successfully"), HttpStatus.NO_CONTENT);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Customer email %s not found in data base", customerVO.getEmailId())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete Customer Service", description = "Delete Customer Data", tags = {"RNT Delete Customer"})
    @DeleteMapping(value = "/delete-customer-by-id/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> deleteCustomer(@PathVariable Long customerId){
        Optional<Map<String, Object>> customerVODB = customerService.findByCustomerId(customerId);
        if(customerVODB.isPresent()){
            String response = customerService.deleteCustomer(customerId);
            return new ResponseEntity<>(appUtil.successResponse(response, RentalConstant.RESPONSE_DATA, response), HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Customer Id not found %s in data base", customerId)), HttpStatus.CREATED);
        }
    }
}
