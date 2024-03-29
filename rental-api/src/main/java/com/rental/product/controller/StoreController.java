package com.rental.product.controller;

import com.rental.product.constant.RentalConstant;
import com.rental.product.mapper.ResponseVO;
import com.rental.product.mapper.StoreVO;
import com.rental.product.services.IStoreService;
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

@Tag(name = "RCT Store Service", description = "Store Store API")
@RestController
@RequestMapping("/rct/store")
public class StoreController {

    private final AppUtil appUtil;

    private final IStoreService storeService;

    @Autowired
    public StoreController(AppUtil appUtil, IStoreService storeService) {
        this.appUtil = appUtil;
        this.storeService = storeService;
    }

    @Operation(summary = "Get All Store Service", description = "Find All Store Data", tags = {"RCT Get All Store"})
    @GetMapping(value = "/get-all-store", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getAll(){
        return new ResponseEntity<>(appUtil.successResponse(storeService.findAllStore(), RentalConstant.RESPONSE_DATA, "Get All Store details successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Get Store By Id Service", description = "Find Store By Id Data", tags = {"RCT Get Store By Id"})
    @GetMapping(value = "/get-by-store-id/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getByStoreId(@PathVariable Long storeId){
        Optional<StoreVO> StoreVO = storeService.findByStoreId(storeId);
        return StoreVO.map(vo -> new ResponseEntity<>(appUtil.successResponse(vo, RentalConstant.RESPONSE_DATA, "Get Store By Id details successfully"), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(appUtil.failedResponse("No Data Found", "Store Data Not Found in Data base"), HttpStatus.BAD_REQUEST));
    }

    @Operation(summary = "Save Store Service", description = "Save Store Data", tags = {"RCT Save Store"})
    @PostMapping(value = "/save-store", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveStore(@RequestBody StoreVO storeVO){
        Optional<StoreVO> StoreVODb = storeService.findByStoreName(storeVO.getName());
        if(StoreVODb.isEmpty()){
            String error = storeService.validateStoreDetails(storeVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(storeService.saveStore(storeVO), RentalConstant.RESPONSE_DATA,"Store data created successfully"), HttpStatus.CREATED);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Store email already exists %s in data base", storeVO.getName())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Save Store Service", description = "Save Store Data", tags = {"RCT Save Store"})
    @PutMapping(value = "/update-store/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveStore(@PathVariable Long storeId, @RequestBody StoreVO storeVO){
        Optional<StoreVO> storeVODb = storeService.findByStoreName(storeVO.getName());
        if(storeVODb.isPresent()){
            String error = storeService.validateStoreDetails(storeVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(storeService.saveStore(storeVO), RentalConstant.RESPONSE_DATA,"Store data updated successfully"), HttpStatus.NO_CONTENT);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Store name %s not found in data base", storeVO.getName())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete Store Service", description = "Delete Store Data", tags = {"RCT Delete Store"})
    @DeleteMapping(value = "/delete-store-by-id/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> deleteStore(@PathVariable Long storeId){
        Optional<StoreVO> StoreVODB = storeService.findByStoreId(storeId);
        if(StoreVODB.isPresent()){
            String response = storeService.deleteStore(storeId);
            return new ResponseEntity<>(appUtil.successResponse(response, RentalConstant.RESPONSE_DATA, response), HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Store Id not found %s in data base", storeId)), HttpStatus.CREATED);
        }
    }
}
