package com.itxwl.shiroserver.service;

import com.itxwl.shiroserver.entity.RolePermission;
import com.itxwl.shiroserver.entity.User;
import com.itxwl.shiroserver.entity.UserInformation;
import com.itxwl.shiroserver.entity.UserRoles;
import com.itxwl.shiroserver.exception.ExceptionEnum;
import com.itxwl.shiroserver.exception.MyException;
import com.itxwl.shiroserver.repository.RoleRepository;
import com.itxwl.shiroserver.repository.RplePermissionRepository;
import com.itxwl.shiroserver.repository.UserRepository;
import com.itxwl.shiroserver.util.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class LoginJwtProceService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JwtUtils jwtUtilWith;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RplePermissionRepository rplePermissionRepository;
    /**
     * 获取tocken
     * @param username
     * @param password
     * @return
     */
    public String findJwtProce(String username, String password) {
        //校验用户名密码是否正确
        String sqlPassword = getPasswordByUserName(username);
        if (StringUtils.isEmpty(sqlPassword)){
            throw  new  MyException(ExceptionEnum.USERNAME_NO_EXISTENCE);
        }
        SimpleHash simpleHash=new SimpleHash("md5",password,username);
        if (!simpleHash.toString().equals(sqlPassword)){
            throw  new MyException(ExceptionEnum.USERNAME_AND_PSD_ERROR);
        }
        JwtUtils jwtUtils=new JwtUtils(jwtUtilWith.getKey(),jwtUtilWith.getTtl());
        Map<String,Object> map=new HashMap<>();
        map.put("demo","demo1");
        //创建JWT加密tocken
        String tocken = jwtUtils.createJwt(username, password,map);
        return tocken;
    }
    private String getPasswordByUserName(String username) {
        String sql = "select password from users where  username = " + "'" + username + "'";
        String password = null;
        try {
            jdbcTemplate.setDataSource(dataSource);
            password = jdbcTemplate.queryForObject(sql, String.class);
            //jdbcTemplate 在查询字符串是null时会抛出异常
        } catch (Exception e) {
            throw  new MyException(ExceptionEnum.EXCEPTION_RUN_ERROR);
        }
        return password;
    }

    public UserInformation findUserInformation(String username) {
        UserInformation userInformation=new UserInformation();
        User user = userRepository.findUserByUsername(username);
        List<UserRoles> allById = roleRepository.findAllById(user.getRoleId());
        LinkedList<RolePermission> rolePermissions=new LinkedList<>();
        for (UserRoles userRoles : allById) {
            RolePermission rolePermissionById = rplePermissionRepository.findRolePermissionById(userRoles.getId());
            rolePermissions.add(rolePermissionById);
        }
        userInformation.setUserId(user.getId());
        userInformation.setUserName(user.getUsername());
        userInformation.setUserRoles(allById);
        userInformation.setPermissions(rolePermissions);
        return userInformation;
    }
}
