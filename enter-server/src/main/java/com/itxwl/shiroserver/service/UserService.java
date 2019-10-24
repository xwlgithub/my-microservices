package com.itxwl.shiroserver.service;

import com.itxwl.shiroserver.entiry.PermissionDto;
import com.itxwl.shiroserver.entiry.User;
import com.itxwl.shiroserver.exception.MyException;

import java.util.List;

public interface UserService  {
    /**
     * 保存
     * @param user
     * @return
     */
    Boolean saveUser(User user) throws Exception;

    /**
     * 删除
     * @param id
     * @return
     */
    Integer deletes(String id) throws Exception;

    /**
     * 查询所有
     * @return
     */
    User selectById(String id)throws MyException;

    List<User> findAll(String name);

    void update(User user);

    Boolean Login(String name, String password) throws MyException;
    User getUser(String name, String password) throws MyException;

    /**
     * 分配角色
     * @param userId
     * @param roleIds
     * @return
     */
    Boolean withRole(String userId, String roleIds,String deleIds);

    PermissionDto findUserPermissions(String tocken);
}
