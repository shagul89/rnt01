package com.rental.product.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "RNT_REFRESH_TOKEN")
public class RefreshTokenEntity implements Serializable {

    private static final long serialVersionUID = 1234567L;

    @Id
    @Column(name = "REFRESH_TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "EXPIRED_DATE")
    private Instant expiryDate;

    @Column(name = "USER_NAME")
    private String userName;
}
