package com.itxwl.shiroserver.entiry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Data
@Table(name = "user")
@Entity
public class User  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id",nullable = true)
    private String id;
    @Column(name = "name",nullable = true)
    private String name;
    @Column(name = "password",nullable = true)
    @JsonIgnore
    private String password;
    @Column(name = "email",nullable = true)
    private String email;
    @Column(name = "phoneNumber",nullable = true)
    private String phoneNumber;

    @Column(name = "remark",nullable = true)
    private String remark;
    /**
     * 用户与角色多对多
     * @JsonIgnore 忽略返回
     *
     * @param id
     * @param name
     * @param password
     * @param phoneNumber
     */
    @Transient
    private Set<Role> roles=new HashSet<>();

    public User (String id, String name, String password,String phoneNumber,String remark){
        this.id=id;
        this.name=name;
        this.password=password;
        this.phoneNumber=phoneNumber;
        this.remark=remark;
    }
}
