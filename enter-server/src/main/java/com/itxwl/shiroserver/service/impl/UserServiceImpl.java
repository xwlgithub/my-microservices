package com.itxwl.shiroserver.service.impl;

import com.itxwl.shiroserver.entiry.User;
import com.itxwl.shiroserver.exception.ExceptionEnum;
import com.itxwl.shiroserver.exception.MyException;
import com.itxwl.shiroserver.respotitory.UserRepository;
import com.itxwl.shiroserver.service.UserService;
import com.itxwl.shiroserver.utils.IdWorker;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private JdbcTemplate jdbcTemplate;
    private IdWorker idWorker;

    /**
     * 保存
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Boolean saveUser(User user) throws Exception{
        User save = null;
        try {
            user.setId(idWorker.nextId()+"");
            if (StringUtils.isEmpty(user.getPassword())){
                user.setPassword("123456");
            }
            save = userRepository.save(user);
        } catch (Exception e) {
            throw  new  RuntimeException("保存失败!");
        }
        if (save!=null){
            return true;
        }
        return false;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public Integer deletes(String id)  throws Exception{
        try {
            userRepository.delete(id);
        } catch (Exception e) {
            throw  new RuntimeException("删除失败！");
        }
        int i = (int) Math.random()*100 + 1;
        return i;
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public User selectById(String id)throws MyException {
        User user=null;
        try {
            user = userRepository.findOne(id);
        } catch (Exception e) {
            throw  new MyException(ExceptionEnum.EXCEPTION_RUN_ERROR);
        }
        return user;
    }

    @Override
    public List<User> findAll(String name) {
        List<User> userList=null;
        if (StringUtils.isNotEmpty(name)){
            String sql="select *  from user where name like  '%"+name+"%'";
            userList = jdbcTemplate.query(sql, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                 //   BigInteger bigInteger = new BigInteger(String.valueOf(resultSet.getInt("phone_number")));
                    return new User(resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"), resultSet.getString("phone_number"),resultSet.getString("remark"));
                }
            });
        }
       else  {
            userList=userRepository.findAll();
        }
        return userList;
    }

    @Override
    public void update(User user) {
        if (user.getId()!=null){
           userRepository.save(user);
        }
    }

    @Override
    public Boolean Login(String name, String password) throws MyException {
        User user = userRepository.findUserByName(name);
        if (user==null){
            throw  new MyException(ExceptionEnum.USERNAME_NO_EXISTENCE);
        }else {
            if (!user.getPassword().equals(password)){
                throw  new MyException(ExceptionEnum.USERNAME_AND_PSD_ERROR);
            }
        }
        return true;
    }

}
