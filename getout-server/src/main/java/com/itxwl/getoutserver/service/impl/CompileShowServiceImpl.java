package com.itxwl.getoutserver.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.itxwl.getoutserver.config.DataSourceContext;
import com.itxwl.getoutserver.service.CompileShowService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CompileShowServiceImpl  implements CompileShowService  {
    private JdbcTemplate jdbcTemplate;
    /**
     * 这个是动态切换数据源  我要修改的就是这个注解值
     * @param
     * @return
     */
    //@DS("master")
    public String findAtimerByName() {//54
//        if ("1".equals("1")){
//            DataSourceContext.setDataSource("other");
//        }
        System.out.println("执行");
        String sql="select meter_name  from e_dayenergy where id =1";
        return jdbcTemplate.queryForObject(sql,String.class);
    }

}
