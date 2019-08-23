package com.itxwl.shiroserver.realm;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义realm
 */
@SuppressWarnings("all")
@Component
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    public static CustomRealm customRealm;

    /**
     * 初始化普通类之前 将数据源和JDBC模板赋值给当前Bean
     * 之后将该Bean添加SpringBoot管理
     * SpringBoot好像普通类在运行时自动注入都会找不到,原因应该是Springboot的某种特性吧
     */
    @PostConstruct
    public void  inits(){
        customRealm=this;
        customRealm.dataSource=this.dataSource;
        customRealm.jdbcTemplate=this.jdbcTemplate;
    }

    //校验该用户有哪些权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //获取角色数据
        Set<String> users = getRolesByUserName(userName);
        Set<String> permisss = getPermissionByUserName(userName);
        SimpleAuthorizationInfo so = new SimpleAuthorizationInfo();
        so.setStringPermissions(permisss);
        so.setRoles(users);
        return so;
    }

    //该角色有什么权限
    private Set<String> getPermissionByUserName(String userName) {
        Set<String> rolesName = new HashSet<String>();
        ResultSet resultSet = null;
        String roleSql = "SELECT permission FROM\n" +
                "roles_permissions WHERE role_name = (select role_name from user_roles where username =" + "'" + userName + "')";
        try {
            customRealm.jdbcTemplate.setDataSource(customRealm.dataSource);
            List<String> permission = customRealm.jdbcTemplate.query(roleSql, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("permission");
                }
            });
            rolesName.addAll(permission);

            return rolesName;
        } catch (Exception e) {
            throw new AuthenticationException("获得角色权限失败");
        }
    }

    //用户属于什么角色
    private Set<String> getRolesByUserName(String userName) {
        Set<String> rolesName = new HashSet<String>();
        String roleSql = "select role_name from user_roles where username = " + "'" + userName + "'";
        try {
            customRealm.jdbcTemplate.setDataSource(customRealm.dataSource);
            List<String> role_name = customRealm.jdbcTemplate.query(roleSql, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("role_name");
                }
            });
            rolesName.addAll(role_name);
        } catch (Exception e) {
            throw new AuthenticationException("获得用户的角色失败");
        }
        return rolesName;

    }

    //校验用户名密码是否正确
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获得用户输入账号
        String username = (String) authenticationToken.getPrincipal();
        //数据库校验
        String password = getPasswordByUserName(username);
        //如果密码为空 则用户不存在
        //如果不为空 SimpleAuthenticationInfo做身份验证处理
        //加盐加密
        //如果用户名不存在shiro底层会抛出UnknownAccountException
        if (StringUtils.isEmpty(password)) {
            throw new UnknownAccountException("用户名不存在");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, "CustomRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return simpleAuthenticationInfo;
    }

    //根据用户名获得密码
    @SuppressWarnings("all")
    private String getPasswordByUserName(String username) {
        String sql = "select password from users where  username = " + "'" + username + "'";
        String password = null;
        try {
            customRealm.jdbcTemplate.setDataSource(customRealm.dataSource);
            password = customRealm.jdbcTemplate.queryForObject(sql, String.class);
            //jdbcTemplate 在查询字符串是null时会抛出异常
        } catch (EmptyResultDataAccessException e) {
            throw  new AuthenticationException("用户名不存在");
        }
        return password;
    }
}
