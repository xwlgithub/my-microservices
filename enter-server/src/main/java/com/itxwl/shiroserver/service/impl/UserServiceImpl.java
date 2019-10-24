package com.itxwl.shiroserver.service.impl;

import com.itxwl.shiroserver.dto.PermisssCode;
import com.itxwl.shiroserver.entiry.PermissionDto;
import com.itxwl.shiroserver.entiry.Role;
import com.itxwl.shiroserver.entiry.User;
import com.itxwl.shiroserver.exception.ExceptionEnum;
import com.itxwl.shiroserver.exception.MyException;
import com.itxwl.shiroserver.respotitory.RoleRepository;
import com.itxwl.shiroserver.respotitory.UserRepository;
import com.itxwl.shiroserver.service.UserService;
import com.itxwl.shiroserver.utils.IdWorker;
import com.itxwl.shiroserver.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private JdbcTemplate jdbcTemplate;
    private IdWorker idWorker;
    private RoleRepository roleRepository;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 保存
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Boolean saveUser(User user) throws Exception {
        User save = null;
        try {
            user.setId(idWorker.nextId() + "");
            if (StringUtils.isEmpty(user.getPassword())) {
                user.setPassword("123456");
            }
            save = userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("保存失败!");
        }
        if (save != null) {
            return true;
        }
        return false;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public Integer deletes(String id) throws Exception {
        try {
            userRepository.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("删除失败！");
        }
        int i = (int) Math.random() * 100 + 1;
        return i;
    }

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public User selectById(String id) throws MyException {
        User user = null;
        try {
            user = userRepository.findOne(id);
        } catch (Exception e) {
            throw new MyException(ExceptionEnum.EXCEPTION_RUN_ERROR);
        }
        return user;
    }

    @Override
    public List<User> findAll(String name) {
        List<User> userList = null;
        if (StringUtils.isNotEmpty(name)) {
            String sql = "select *  from user where name like  '%" + name + "%'";
            userList = jdbcTemplate.query(sql, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    //   BigInteger bigInteger = new BigInteger(String.valueOf(resultSet.getInt("phone_number")));
                    return new User(resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("remark"));
                }
            });
        } else {
            userList = userRepository.findAll();
        }
        return userList;
    }

    @Override
    public void update(User user) {
        if (user.getId() != null) {
            userRepository.save(user);
        }
    }

    @Override
    public Boolean Login(String name, String password) throws MyException {
        User user = userRepository.findUserByName(name);
        if (user == null) {
            throw new MyException(ExceptionEnum.USERNAME_NO_EXISTENCE);
        } else {
            if (!user.getPassword().equals(password)) {
                throw new MyException(ExceptionEnum.USERNAME_AND_PSD_ERROR);
            }
        }
        return true;
    }
    @Override
    public User getUser(String name, String password) throws MyException {
        User user = userRepository.findUserByName(name);
        return user;
    }

    /**
     * 分配角色逻辑
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    @Transactional
    public Boolean withRole(String userId, String roleIds,String deIds) {
        User user = userRepository.findOne(userId);
        if (user==null) {
           throw  new MyException(ExceptionEnum.AUTH_NO_EVERYS);
        }
        //已经有的权限ID
        List<String> role_id = jdbcTemplate.query("select role_id from ro_user where  user_id =" + userId, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("role_id");
            }
        });
        String roleIdList = role_id.toString();
        String[] split = roleIds.split(",");
        if (split.length != 0) {
            for (String roleId : split) {
                //如果不包含 插入新的角色信息
                if(roleIdList.contains(roleId)==false){
                  String sql ="insert into ro_user(role_id,user_id) values ("+roleId+","+userId+")";
                    jdbcTemplate.update(sql);
                }

            }
        }
        if (StringUtils.isNotEmpty(deIds)) {
            String[] split1 = deIds.split(",");
            try {
                for (String s : split1) {
                    String sqldelete = "delete  from ro_user where user_id=" + userId + " and role_id=" + s;
                    jdbcTemplate.update(sqldelete);
                }
            } catch (DataAccessException e) {
                // throw  new MyException(ExceptionEnum.DELETE_ERROR);
                e.printStackTrace();
            }
        }
        return true;
    }
    //识别tocken 返回该用户所属权限信息
    @Override
    public PermissionDto findUserPermissions(String tocken) {
        Claims analysis = jwtUtil.analysis(tocken);
        String id = analysis.getId();
        List<String> permissionListIds=new LinkedList<>();
        List<PermisssCode> permisssCodes = jdbcTemplate.query("SELECT\n" +
                "id,pe_name,pe_code\n" +
                "FROM\n" +
                "\tprimission \n" +
                "WHERE\n" +
                "\tid IN ( SELECT pe_id FROM pe_role WHERE ro_id IN ( SELECT role_id FROM ro_user WHERE user_id = '" + id + "' ) )", new RowMapper<PermisssCode>() {
            @Override
            public PermisssCode mapRow(ResultSet resultSet, int i) throws SQLException {
                PermisssCode permisssCode = new PermisssCode();
                permisssCode.setPeCode(resultSet.getString("pe_code"));
                permisssCode.setPeName(resultSet.getString("pe_name"));
                permisssCode.setId(resultSet.getString("id"));
                permissionListIds.add(permisssCode.getId());
                return permisssCode;
            }
        });
        List<String> apis=null;
        List<String> pointCodes=null;
        if (permisssCodes.size()!=0){
            String permissStrings = StringUtils.join(permissionListIds, ",");
             apis = jdbcTemplate.query("\tSELECT api_method FROM pe_permission_api WHERE permission_id in " +
                    "(" + permissStrings + ")\n", new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("api_method");
                }
            });
             pointCodes = jdbcTemplate.query("\tSELECT point_code FROM pe_permission_point WHERE permission_id in " +
                    "(" + permissStrings + ")", new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("point_code");
                }
            });
        }
        if (permisssCodes.size()==0){
            return new PermissionDto();
        }
        Set<PermisssCode> codes=new HashSet<>(permisssCodes);
        Set<String> aPis=null;
        if (apis.size()==0){
             aPis=new HashSet<>();
        }else {
             aPis=new HashSet<>(apis);
        }
        Set<String> points=null;
        if (pointCodes.size()==0){
            points=new HashSet<>();
        }else {
            points=new HashSet<>(pointCodes);
        }
        return new PermissionDto(codes,aPis,points);
    }
}