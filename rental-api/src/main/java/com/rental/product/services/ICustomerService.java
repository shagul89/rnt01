package com.rental.product.services;

import com.rental.product.mapper.CustomerVO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICustomerService {
    Map<String, Object> findAllCustomer();

    Optional<Map<String, Object>> findByCustomerId(Long customerId);

    Optional<CustomerVO> findByCustomerEmail(String emailId);

    String validateCustomerDetails(CustomerVO customerVO);

    CustomerVO saveCustomer(CustomerVO customerVO);

    String deleteCustomer(Long customerId);
}
