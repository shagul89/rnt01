package com.rental.product.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "RNT_MENU")
public class MenuEntity extends AuditorEntity implements Serializable {

        private static final long serialVersionUID = 1234567L;

        @Id
        @Column(name = "MENU_ID")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long menuId;

        @Column(name = "DISPLAY_NAME")
        private String displayName;

        @Column(name = "DESCRIPTION")
        private String description;

        @Column(name = "ICON")
        private String icon;

        @Column(name = "ROUTE_URL")
        private String route;

        @Column(name = "ACTIVE")
        private boolean defaultSelected;

        @Column(name = "SORT_ORDER")
        private long sortOrder;

}
