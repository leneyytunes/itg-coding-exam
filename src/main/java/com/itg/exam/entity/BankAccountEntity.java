package com.itg.exam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Setter
@Getter
@Entity
@Table(name = "Bank_Account_Details")
public class BankAccountEntity {
    @Id
    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private String accountType;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "AVAILABLE_BALANCE", nullable = false)
    private Long availableBalance;

}
