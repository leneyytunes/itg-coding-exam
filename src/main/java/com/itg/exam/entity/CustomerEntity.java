package com.itg.exam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String customerName;

    @NotNull
    @Column(name = "CUSTOMER_MOBILE", nullable = false)
    private String customerMobile;

    @NotNull
    @Column(name = "CUSTOMER_EMAIL", nullable = false)
    private String customerEmail;

    @Column(name = "ADDRESS_1")
    private String address1;

    @Column(name = "ADDRESS_2")
    private String address2;

    @NotNull
    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private String accountType;

}
