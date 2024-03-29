package com.rental.product.controller;

import com.rental.product.constant.RentalConstant;
import com.rental.product.mapper.ConfigurationVO;
import com.rental.product.mapper.ResponseVO;
import com.rental.product.services.IConfigurationService;
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

@Tag(name = "RNT Configuration Service", description = "Configuration Service API")
@RestController
@RequestMapping("/rnt/configuration")
public class ConfigurationController {

    private final IConfigurationService configurationService;

    private final AppUtil appUtil;

    @Autowired
    public ConfigurationController(IConfigurationService configurationService, AppUtil appUtil) {
        this.configurationService = configurationService;
        this.appUtil = appUtil;
    }

    @Operation(summary = "Get All Configuration Service", description = "Find All Configuration Data", tags = {"RNT Get All Configuration"})
    @GetMapping(value = "/get-all-configuration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getAll(){
        return new ResponseEntity<>(appUtil.successResponse(configurationService.findAllConfiguration(), RentalConstant.RESPONSE_DATA, "Get All Configuration details successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Get Configuration By Id Service", description = "Find Configuration By Id Data", tags = {"RNT Get Configuration By Id"})
    @GetMapping(value = "/get-by-configuration-id/{configurationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getByConfigurationId(@PathVariable Long configurationId){
        Optional<ConfigurationVO> configurationVO = configurationService.findByConfigurationId(configurationId);
        return configurationVO.map(vo -> new ResponseEntity<>(appUtil.successResponse(vo, RentalConstant.RESPONSE_DATA, "Get Configuration By Id details successfully"), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(appUtil.failedResponse("No Data Found", "Configuration Data Not Found in Data base"), HttpStatus.BAD_REQUEST));
    }

    @Operation(summary = "Save Configuration Service", description = "Save Configuration Data", tags = {"RNT Save Configuration"})
    @PostMapping(value = "/save-configuration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveConfiguration(@RequestBody ConfigurationVO configurationVO){
        Optional<ConfigurationVO> configurationVODb = configurationService.findByConfigurationName(configurationVO.getName());
        if(configurationVODb.isEmpty()){
            String error = configurationService.validateConfigurationDetails(configurationVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(configurationService.saveConfiguration(configurationVO), RentalConstant.RESPONSE_DATA,"Configuration data created successfully"), HttpStatus.CREATED);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Configuration name already exists %s in data base", configurationVO.getName())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Save Configuration Service", description = "Save Configuration Data", tags = {"RNT Save Configuration"})
    @PutMapping(value = "/update-configuration/{configurationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveConfiguration(@PathVariable Long ConfigurationId, @RequestBody ConfigurationVO configurationVO){
        Optional<ConfigurationVO> configurationVODb = configurationService.findByConfigurationName(configurationVO.getName());
        if(configurationVODb.isPresent()){
            String error = configurationService.validateConfigurationDetails(configurationVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(configurationService.saveConfiguration(configurationVO), RentalConstant.RESPONSE_DATA,"Configuration data updated successfully"), HttpStatus.NO_CONTENT);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Configuration name %s not found in data base", configurationVO.getName())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete Configuration Service", description = "Delete Configuration Data", tags = {"RNT Delete Configuration"})
    @DeleteMapping(value = "/delete-configuration-by-id/{configurationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> deleteConfiguration(@PathVariable Long configurationId){
        Optional<ConfigurationVO> ConfigurationVODB = configurationService.findByConfigurationId(configurationId);
        if(ConfigurationVODB.isPresent()){
            String response = configurationService.deleteConfiguration(configurationId);
            return new ResponseEntity<>(appUtil.successResponse(response, RentalConstant.RESPONSE_DATA, response), HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Configuration Id not found %s in data base", configurationId)), HttpStatus.CREATED);
        }
    }
}
