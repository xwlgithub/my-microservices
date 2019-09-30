package com.itxwl.shiroserver.entiry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "role")
@ToString
public class Role {

    @Id
    private String id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_remark")
    private String roleRemark;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "pe_role",joinColumns = {
            @JoinColumn(name = "ro_id",referencedColumnName = "id")
    },inverseJoinColumns ={@JoinColumn(name = "pe_id",referencedColumnName = "id")})
    private Set<Permission>  permissions=new HashSet<>();



}
