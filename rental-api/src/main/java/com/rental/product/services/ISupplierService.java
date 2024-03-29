package com.rental.product.services;

import com.rental.product.mapper.SupplierVO;
import java.util.List;
import java.util.Optional;

public interface ISupplierService {
    List<SupplierVO> findAllSupplier();

    Optional<SupplierVO> findBySupplierId(Long supplierId);

    Optional<SupplierVO> findBySupplierEmail(String emailId);

    String validateSupplierDetails(SupplierVO supplierVO);

    SupplierVO saveSupplier(SupplierVO supplierVO);

    String deleteSupplier(Long supplierId);
}
