package com.itxwl.getoutserver.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.itxwl.getoutserver.service.CompileShowService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompileShowServiceImpl  implements CompileShowService  {
    private JdbcTemplate jdbcTemplate;

    /**
     * 这个是动态切换数据源  我要修改的就是这个注解值
     * 我现在登录得用户 它是要访问不
     * @param id
     * @return
     */
    @DS("master")
    public String findAtimerByName(String id) {//54
       // String sql="select name from user where id =35";
        String sql="select meter_name from e_dayenergy where id =1";
        System.out.println("执行");
        return jdbcTemplate.queryForObject(sql,String.class);
    }




}
