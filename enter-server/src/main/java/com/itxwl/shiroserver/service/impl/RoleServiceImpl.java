package com.itxwl.shiroserver.service.impl;

import com.itxwl.shiroserver.entiry.Role;
import com.itxwl.shiroserver.entiry.User;
import com.itxwl.shiroserver.exception.ExceptionEnum;
import com.itxwl.shiroserver.exception.MyException;
import com.itxwl.shiroserver.respotitory.RoleRepository;
import com.itxwl.shiroserver.respotitory.UserRepository;
import com.itxwl.shiroserver.service.RoleService;
import com.itxwl.shiroserver.utils.IdWorker;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;
    private IdWorker idWorker;
    private JdbcTemplate jdbcTemplate;
    private UserRepository userRepository;

    //新增角色
    @Override
    @Transactional
    public Boolean add(Role role) {
        role.setId(idWorker.nextId() + "");
        role.setCreateTime(new Date());
        Role save = roleRepository.save(role);
        return save != null ? true : false;
    }
    //查询
    @Override
    public List<Role> findAll() {
        String sql ="";
            sql="select * from role order by create_time desc";
        List<Role> list= jdbcTemplate.query(sql, new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Role(resultSet.getString("id"),
                        resultSet.getString("role_name"),
                        resultSet.getString("role_remark"),resultSet.getDate("create_time"),
                        null);
            }
        });
            return list;
    }

    @Override
    public List<String> findRolesByUserId(String userId)  throws MyException {
        List<String> list= null;
        try {
            String sql="select role_id from ro_user where user_id = "+userId;
            list = jdbcTemplate.query(sql, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("role_id");
                }
            });
        } catch (Exception e) {
            throw  new MyException(ExceptionEnum.EXCEPTION_RUN_ERROR);
        }
        return list;
    }
}
