package com.rental.product.controller;

import com.rental.product.constant.RentalConstant;
import com.rental.product.mapper.MenuVO;
import com.rental.product.mapper.ResponseVO;
import com.rental.product.services.IMenuService;
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

@Tag(name = "RNT Menu Service", description = "Menu Service API")
@RestController
@RequestMapping("/rnt/menu")
public class MenuController {

    private final IMenuService menuService;
    private final AppUtil appUtil;


    @Autowired
    public MenuController(IMenuService menuService, AppUtil appUtil) {
        this.menuService = menuService;
        this.appUtil = appUtil;
    }

    @Operation(summary = "Get All Menu Service", description = "Find All Menu Data", tags = {"RCT Get All Menu"})
    @GetMapping(value = "/get-all-menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getAll(){
        return new ResponseEntity<>(appUtil.successResponse(menuService.findAllMenu(), RentalConstant.RESPONSE_DATA, "Get All Menu details successfully"), HttpStatus.OK);
    }

    @Operation(summary = "Get Menu By Id Service", description = "Find Menu By Id Data", tags = {"RCT Get Menu By Id"})
    @GetMapping(value = "/get-by-menu-id/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> getByMenuId(@PathVariable Long menuId){
        Optional<MenuVO> MenuVO = menuService.findByMenuId(menuId);
        return MenuVO.map(vo -> new ResponseEntity<>(appUtil.successResponse(vo, RentalConstant.RESPONSE_DATA, "Get Menu By Id details successfully"), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(appUtil.failedResponse("No Data Found", "Menu Data Not Found in Data base"), HttpStatus.BAD_REQUEST));
    }

    @Operation(summary = "Save Menu Service", description = "Save Menu Data", tags = {"RCT Save Menu"})
    @PostMapping(value = "/save-menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveMenu(@RequestBody MenuVO menuVO){
        Optional<MenuVO> menuVODb = menuService.findByMenuDisplayName(menuVO.getDisplayName());
        if(menuVODb.isEmpty()){
            String error = menuService.validateMenuDetails(menuVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(menuService.saveMenu(menuVO), RentalConstant.RESPONSE_DATA,"Menu data created successfully"), HttpStatus.CREATED);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Menu name already exists %s in data base", menuVO.getDisplayName())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Save Menu Service", description = "Save Menu Data", tags = {"RCT Save Menu"})
    @PutMapping(value = "/update-Menu/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> saveMenu(@PathVariable Long menuId, @RequestBody MenuVO menuVO){
        Optional<MenuVO> MenuVODb = menuService.findByMenuDisplayName(menuVO.getDisplayName());
        if(MenuVODb.isPresent()){
            String error = menuService.validateMenuDetails(menuVO);
            if(!StringUtils.isBlank(error)){
                return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Please validate input for %s", error)), HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(appUtil.successResponse(menuService.saveMenu(menuVO), RentalConstant.RESPONSE_DATA,"Menu data updated successfully"), HttpStatus.NO_CONTENT);
            }
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Menu email %s not found in data base", menuVO.getDisplayName())), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete Menu Service", description = "Delete Menu Data", tags = {"RCT Delete Menu"})
    @DeleteMapping(value = "/delete-Menu/{menuId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseVO> deleteMenu(@PathVariable Long menuId){
        Optional<MenuVO> MenuVODB = menuService.findByMenuId(menuId);
        if(MenuVODB.isPresent()){
            String response = menuService.deleteMenu(menuId);
            return new ResponseEntity<>(appUtil.successResponse(response, RentalConstant.RESPONSE_DATA, response), HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(appUtil.failedResponse(RentalConstant.INPUT_ERROR,String.format("Menu Id not found %s in data base", menuId)), HttpStatus.CREATED);
        }
    }
}
