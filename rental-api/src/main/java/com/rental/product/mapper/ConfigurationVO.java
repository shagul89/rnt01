package com.rental.product.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfigurationVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private long configId;
    private String name;
    private String value;
    private String description;
    private String createdBy;
    private Date createdDate;
    private String updatedName;
    private Date updatedDate;
}
