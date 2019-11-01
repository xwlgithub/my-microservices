package com.itxwl.getoutserver.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.itxwl.getoutserver.service.CompileShowService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class CompileShowServiceImpl  implements CompileShowService  {
    /**
     * 这个是动态切换数据源  我要修改的就是这个注解值
     * @param
     * @return
     */
    private JdbcTemplate jdbcTemplate;

    @DS("master")
    public String findAtimerByName() {//54
        System.out.println("执行");
    String sql="select meter_name  from e_dayenergy where id =1";
        return jdbcTemplate.queryForObject(sql,String.class);
    }

}
