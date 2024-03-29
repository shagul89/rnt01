package com.rental.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "RNT_CUSTOMER")
public class CustomerEntity extends AuditorEntity implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Id
    @Column(name = "CUSTOMER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long customerId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String emailId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<OrderEntity> order = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "RNT_CUSTOMER_BILLING_ADDRESS", joinColumns = { @JoinColumn(name = "CUSTOMER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ADDRESS_ID") })
    private Set<AddressEntity> billingAddress = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "RNT_CUSTOMER_SHIPPING_ADDRESS", joinColumns = { @JoinColumn(name = "CUSTOMER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ADDRESS_ID") })
    private Set<AddressEntity> shippingAddress = new HashSet<>();

}
