package com.rental.product.model;

import com.rental.product.enumeration.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "RNT_PAYMENT")
public class PaymentEntity extends AuditorEntity implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Id
    @Column(name = "PAYMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    @Column(name = "PAYMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_METHOD")
    private PaymentType paymentMethod;

    @Column(name = "TOTAL_AMOUNT")
    private double totalAmount;
}
