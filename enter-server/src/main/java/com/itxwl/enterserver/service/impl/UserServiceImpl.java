package com.itxwl.enterserver.service.impl;

import com.itxwl.enterserver.entiry.User;
import com.itxwl.enterserver.exception.ExceptionEnum;
import com.itxwl.enterserver.exception.MyException;
import com.itxwl.enterserver.respotitory.UserRepository;
import com.itxwl.enterserver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    /**
     * 保存
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public Boolean saveUser(User user) throws Exception{
        User save = null;
        try {
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
    public Integer deletes(Long id)  throws Exception{
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
    public User selectById(Long id)throws MyException {
        User user=null;
        try {
            user = userRepository.findOne(id);
            int i=1/0;
        } catch (Exception e) {
            throw  new MyException(ExceptionEnum.EXCEPTION_RUN_ERROR);
        }
        return user;
    }

}
