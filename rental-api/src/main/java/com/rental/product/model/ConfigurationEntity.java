package com.rental.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "RNT_CONFIGURATION")
public class ConfigurationEntity extends AuditorEntity implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Id
    @Column(name = "CONFIGURATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long configId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "DESCRIPTION")
    private String description;

}
