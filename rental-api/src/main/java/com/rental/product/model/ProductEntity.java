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
@Table(name = "RNT_PRODUCT")
public class ProductEntity extends AuditorEntity implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "TYPE_OF_PRODUCT")
    private String typeOfProduct;

    @Column(name = "WARRANTY_PERIOD")
    private int warrantyPeriod;


    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "RNT_PRODUCT_STORE", joinColumns = { @JoinColumn(name = "PRODUCT_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "STORE_ID") })
    private Set<StoreEntity> store = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "RNT_PRODUCT_SUPPLIER", joinColumns = { @JoinColumn(name = "PRODUCT_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "SUPPLIER_ID") })
    private Set<SupplierEntity> supplier = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderItemEntity> orderItem = new HashSet<>();

}
