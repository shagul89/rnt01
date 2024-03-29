package com.rental.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "RNT_ADDRESS")
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Id
    @Column(name = "ADDRESS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "ADDRESS_LINE_1")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2")
    private String addressLine2;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "MOBILE_NO")
    private String mobileNumber;

    @Column(name = "OFFICE_NO")
    private String officeNumber;

    @Column(name = "ALT_CONTACT_NO")
    private String alternateMobileNumber;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "userAddress")
    @JsonIgnore
    private Set<UserEntity> users = new HashSet<UserEntity>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "supplierAddress")
    @JsonIgnore
    private Set<SupplierEntity> supplier = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "billingAddress")
    @JsonIgnore
    private Set<CustomerEntity> customerBillingAddress = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "shippingAddress")
    @JsonIgnore
    private Set<CustomerEntity> customerShippingAddress = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "storeAddress")
    @JsonIgnore
    private Set<StoreEntity> store = new HashSet<>();
}
