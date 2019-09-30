package com.itxwl.shiroserver.entiry;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单栏
 */
@Entity
@Table(name = "pe_permission_point")
@Data
public class PermissionPoint {

    @Id
    private String id;
    //菜单编码
    @Column(name = "point_code")
    private String pointCode;
     //菜单名称
    @Column(name = "point_name")
    private String pointName;

}
