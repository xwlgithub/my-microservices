package com.itxwl.shiroserver.entiry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "role")
@AllArgsConstructor
public class Role implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_remark")
    private String roleRemark;
    @Column(name = "create_time")
    public Date createTime;
    /**
     * 1代表管理员  2代表普通用户
     */
    @Column(name = "role_type")
    private Integer roleType;

    @Transient
    private Set<User> users = new HashSet<User>();//角色与用户   多对多

    @Transient
    private Set<Permission> permissions = new HashSet<>();

    public Role(String id, String roleName, String roleRemark, Date createTime, Set<User> users) {
        this.id = id;
        this.roleName = roleName;
        this.roleRemark = roleRemark;
        this.users = users;
        this.createTime = createTime;
    }

}
