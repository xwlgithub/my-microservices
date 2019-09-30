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
@Table(name = "pe_permission_api")
@Data
public class PermissionApi {

    @Id
    private String id;
    //api路径
    @Column(name = "api_url")
    private String apiUrl;
     //按钮名称
    @Column(name = "api_method")
    private String apiMethod;

}
