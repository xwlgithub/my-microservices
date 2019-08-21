package com.itxwl.shiroserver.service;

import com.itxwl.shiroserver.entiry.User;
import com.itxwl.shiroserver.exception.MyException;

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
    Integer deletes(Long id) throws Exception;

    /**
     * 查询所有
     * @return
     */
    User selectById(Long id)throws MyException;

}
