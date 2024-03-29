package com.rental.product.mapper;

import com.rental.product.enumeration.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private long paymentId;
    private Date paymentDate;
    private PaymentType paymentMethod;
    private double totalAmount;
    private String createdBy;
    private Date createdDate;
    private String updatedName;
    private Date updatedDate;
}
