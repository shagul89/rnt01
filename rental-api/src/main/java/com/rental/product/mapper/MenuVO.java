package com.rental.product.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private long menuId;
    private String displayName;
    private String description;
    private String icon;
    private String route;
    private boolean defaultSelected;
    private long sortOrder;
}
