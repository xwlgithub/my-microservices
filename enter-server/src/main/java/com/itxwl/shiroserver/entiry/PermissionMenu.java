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
@Table(name = "pe_permission_menu")
@Data
public class PermissionMenu {

    @Id
    private String id;
    //按钮编码
    @Column(name = "menu_code")
    private String menuCode;
     //按钮名称
    @Column(name = "menu_name")
    private String menuName;

}
