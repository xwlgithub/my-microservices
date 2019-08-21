package com.itxwl.shiroserver.entiry;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Table(name = "user")
@Entity
public class User {
    @Id
    @Column(name = "id",nullable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = true)
    private String name;
    @Column(name = "password",nullable = true)
    private String password;
    @Column(name = "email",nullable = true)
    private String email;
    @Column(name = "phoneNumber",nullable = true)
    private BigInteger phoneNumber;
}
