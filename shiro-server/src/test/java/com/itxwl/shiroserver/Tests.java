package com.itxwl.shiroserver;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    @Test
    public void get(){
        jdbcTemplate.setDataSource(dataSource);
        System.out.println("得到数据源"+jdbcTemplate.getDataSource());
        String s = jdbcTemplate.queryForObject("select password from users where  username = 'mark'", String.class);
        System.out.println(s);
    }
}
