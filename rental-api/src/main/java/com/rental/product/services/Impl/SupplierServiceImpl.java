package com.rental.product.services.Impl;

import com.rental.product.exception.RentalException;
import com.rental.product.mapper.CustomerVO;
import com.rental.product.mapper.SupplierVO;
import com.rental.product.model.CustomerEntity;
import com.rental.product.model.SupplierEntity;
import com.rental.product.repository.SupplierRepository;
import com.rental.product.services.ISupplierService;
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
public class SupplierServiceImpl implements ISupplierService {

    private final SupplierRepository supplierRepository;


    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<SupplierVO> findAllSupplier() {
        List<SupplierVO> supplierVOList = new ArrayList<>();
        try{
            List<SupplierEntity> supplierEntityList = supplierRepository.findAll();
            BeanUtils.copyProperties(supplierEntityList, supplierVOList);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return supplierVOList;
    }

    @Override
    public Optional<SupplierVO> findBySupplierId(Long supplierId) {
        SupplierVO supplierVO = new SupplierVO();
        try{
            Optional<SupplierEntity> supplierEntity = supplierRepository.findById(supplierId);
            if(supplierEntity.isPresent()){
                BeanUtils.copyProperties(supplierEntity.get(), supplierVO);
                return Optional.of(supplierVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public Optional<SupplierVO> findBySupplierEmail(String emailId) {
        SupplierVO supplierVO = new SupplierVO();
        try{
            Optional<SupplierEntity> supplierEntity = supplierRepository.findByEmail(emailId);
            if(supplierEntity.isPresent()){
                BeanUtils.copyProperties(supplierEntity.get(), supplierVO);
                return Optional.of(supplierVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public String validateSupplierDetails(SupplierVO supplierVO) {
        AtomicReference<String> error = new AtomicReference<>("");
        try{
            if(StringUtils.isBlank(supplierVO.getEmail())){
                error.set(error + " Supplier Email");
            }
            if(StringUtils.isBlank(supplierVO.getName())){
                error.set(error + " Supplier Name");
            }
            if(supplierVO.getSupplierAddress().isEmpty()){
                error.set(error + " Supplier Billing Address");
            }else {
                supplierVO.getSupplierAddress().forEach(e -> {
                    error.set(AppUtil.validateAddress(e, "Address "));
                });
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return error.get();
    }

    @Override
    public SupplierVO saveSupplier(SupplierVO supplierVO) {
        try{
            SupplierEntity supplierEntity = new SupplierEntity();
            BeanUtils.copyProperties(supplierVO, supplierEntity);
            supplierEntity = supplierRepository.save(supplierEntity);
            BeanUtils.copyProperties(supplierEntity, supplierVO);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return supplierVO;
    }

    @Override
    public String deleteSupplier(Long supplierId) {
        try{
            supplierRepository.deleteById(supplierId);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return "Supplier deleted successfully";
    }
}
