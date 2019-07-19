package com.itxwl.enterserver.service;

import com.itxwl.enterserver.entiry.User;

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
    Integer deletes(Long id) throws Exception;

    /**
     * 查询所有
     * @return
     */
    List<User> selectAll() throws Exception;

}
