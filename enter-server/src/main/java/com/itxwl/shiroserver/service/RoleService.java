package com.itxwl.shiroserver.service;

import com.itxwl.shiroserver.entiry.Role;
import com.itxwl.shiroserver.exception.MyException;

import java.util.List;

public interface RoleService {
    Boolean add(Role role);

    List<Role> findAll(Integer current, Integer size);

    List<String> findRolesByUserId(String userId) throws MyException;
}
