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
@Table(name = "RNT_STORE")
public class StoreEntity extends AuditorEntity implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Id
    @Column(name = "STORE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeId;

    @Column(name = "NAME")
    private String name;


    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "store")
    @JsonIgnore
    private Set<ProductEntity> products = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "RCT_STORE_ADDRESS", joinColumns = { @JoinColumn(name = "STORE_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ADDRESS_ID") })
    private Set<AddressEntity> storeAddress = new HashSet<>();
    
}
