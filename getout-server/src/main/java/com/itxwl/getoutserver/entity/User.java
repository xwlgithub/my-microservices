package com.itxwl.getoutserver.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
@Data
public class User {
    private Long id;
    private String name;
    private String password;
    private String email;
    private BigInteger phoneNumber;
}
