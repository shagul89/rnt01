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
@Table(name = "RNT_SUPPLIER")
public class SupplierEntity extends AuditorEntity implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Id
    @Column(name = "SUPPLIER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long supplierId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;


    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "supplier")
    @JsonIgnore
    private Set<ProductEntity> products = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "RNT_SUPPLIER_ADDRESS", joinColumns = { @JoinColumn(name = "SUPPLIER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ADDRESS_ID") })
    private Set<AddressEntity> supplierAddress = new HashSet<>();

}
