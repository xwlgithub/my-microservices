package com.itxwl.shiroserver.entiry;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * api实体
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
     //api名称
    @Column(name = "api_method")
    private String apiMethod;
    //权限id
    @Column(name = "permission_id")
    private String permissionId;

}
