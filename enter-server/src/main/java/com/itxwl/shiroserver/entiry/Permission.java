package com.itxwl.shiroserver.entiry;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "primission")
public class Permission {
    @Id
    private String id;
    @Column(name = "pe_name")
    private String name;
    /**
     * 1代表菜单
     * 2代表功能按钮
     * 3 API
     */
    @Column(name = "type")
    private Integer type;
    /**
     * 菜单id
     * 菜单级别  默认为0最大
     */
    @Column(name = "pid")
    private String pid;

    @Column(name = "pe_code")
    private String peCode;
}
