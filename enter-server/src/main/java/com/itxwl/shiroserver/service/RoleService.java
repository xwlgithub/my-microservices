package com.itxwl.shiroserver.service;

import com.itxwl.shiroserver.entiry.Role;
import com.itxwl.shiroserver.exception.MyException;

import java.util.List;

public interface RoleService {
    Boolean add(Role role);

    List<Role> findAll();

    List<String> findRolesByUserId(String userId) throws MyException;

    Boolean deleteRoleById(String id) throws MyException;

    Boolean authonRoleById(String roleId, String permissIds,String deteids)throws MyException;
}
