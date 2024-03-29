package com.rental.product.services.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.product.constant.RentalConstant;
import com.rental.product.exception.RentalException;
import com.rental.product.mapper.CustomerVO;
import com.rental.product.model.CustomerEntity;
import com.rental.product.repository.CustomerRepository;
import com.rental.product.services.ICustomerService;
import com.rental.product.util.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Map<String, Object> findAllCustomer() {
        Map<String, Object> map = new HashMap<>();
        try{
            map.put(RentalConstant.LIST_VO, customerRepository.findAll());
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return map;
    }

    @Override
    public Optional<Map<String, Object>> findByCustomerId(Long customerId) {
        Map<String, Object> map = new HashMap<>();
        try{
            Optional<CustomerEntity> customerEntity = customerRepository.findById(customerId);
            if(customerEntity.isPresent()){
                map.put(RentalConstant.DATA_VO, customerEntity.get());
                return Optional.of(map);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public Optional<CustomerVO> findByCustomerEmail(String emailId) {
        CustomerVO customerVO = new CustomerVO();
        try{
            Optional<CustomerEntity> customerEntity = customerRepository.findByEmailId(emailId);
            if(customerEntity.isPresent()){
                BeanUtils.copyProperties(customerEntity.get(), customerVO);
                return Optional.of(customerVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }


    @Override
    public String validateCustomerDetails(CustomerVO customerVO) {
        AtomicReference<String> error = new AtomicReference<>("");
        try{
            if(StringUtils.isBlank(customerVO.getEmailId())){
                error.set(error + " Customer Email");
            }
            if(StringUtils.isBlank(customerVO.getName())){
                error.set(error + " Customer Name");
            }
            if(customerVO.getBillingAddress().isEmpty()){
                error.set(error + " Customer Billing Address");
            }else {
                customerVO.getBillingAddress().forEach(e -> {
                    error.set(AppUtil.validateAddress(e, "Billing Address "));
                });
            }
            if(customerVO.getShippingAddress().isEmpty()){
                error.set(error + " Customer Shipping Address");
            }else {
                customerVO.getShippingAddress().forEach(e -> {
                    error.set(AppUtil.validateAddress(e, "Shipping Address "));
                });
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return error.get();
    }

    @Override
    public CustomerVO saveCustomer(CustomerVO customerVO) {
        ObjectMapper ow = new ObjectMapper();
        try{
            String json = ow.writeValueAsString(customerVO);
            CustomerEntity customerEntity = ow.convertValue(json, CustomerEntity.class);
            customerEntity = customerRepository.save(customerEntity);
            BeanUtils.copyProperties(customerEntity, customerVO);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return customerVO;
    }

    @Override
    public String deleteCustomer(Long customerId) {
        try{
            customerRepository.deleteById(customerId);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return "Customer deleted successfully";
    }
}
