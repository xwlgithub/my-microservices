package com.itxwl.shiroserver.realm;

import com.itxwl.shiroserver.exception.ExceptionEnum;
import com.itxwl.shiroserver.exception.MyException;
import com.itxwl.shiroserver.util.DataSourceMysql;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义realm
 */
@SuppressWarnings("all")
public class CustomRealm extends AuthorizingRealm {

    //校验该用户有哪些权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String)principalCollection.getPrimaryPrincipal();
        //获取角色数据
        Set<String> users=getRolesByUserName(userName);
        Set<String> permisss =getPermissionByUserName(userName);
        SimpleAuthorizationInfo so=new SimpleAuthorizationInfo();
        so.setStringPermissions(permisss);
        so.setRoles(users);
        return so;
    }
    //该角色有什么权限
    private Set<String> getPermissionByUserName(String userName) {
        Set<String> rolesName=new HashSet<String>();
        ResultSet resultSet=null;
        String roleSql="SELECT permission FROM\n" +
                "roles_permissions WHERE role_name = (select role_name from user_roles where username ="+"'"+userName+"')";
        try {
            resultSet = DataSourceMysql.getDataSource().prepareStatement(roleSql).executeQuery();
            while (resultSet.next()){
                rolesName.add(resultSet.getString("permission"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rolesName;
    }

    //用户属于什么角色
    private Set<String> getRolesByUserName(String userName) {
        Set<String> rolesName=new HashSet<String>();
        ResultSet resultSet=null;
        String roleSql="select role_name from user_roles where username = "+"'"+userName+"'";
        try {
            resultSet = DataSourceMysql.getDataSource().prepareStatement(roleSql).executeQuery();
            while (resultSet.next()){
                rolesName.add(resultSet.getString("role_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return rolesName;

    }
    //校验用户名密码是否正确
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws MyException {
        //获得用户输入账号
        String username = (String) authenticationToken.getPrincipal();
        //数据库校验
        String password = getPasswordByUserName(username);
        if (StringUtils.isEmpty(password)) {
            throw new MyException(ExceptionEnum.USERNAME_NO_EXISTENCE);
        }
        //如果密码为空 则用户不存在
        //如果不为空 SimpleAuthenticationInfo做身份验证处理
        //加盐加密
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        try {
            simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, "CustomRealm");
            simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        } catch (Exception e) {
            throw new MyException(ExceptionEnum.USERNAME_AND_PSD_ERROR);
        }
        return simpleAuthenticationInfo;
    }

    @SuppressWarnings("all")
    private String getPasswordByUserName(String username)throws MyException {
        Connection connection = null;
        String psd = null;
        try {
            connection = DataSourceMysql.getDataSource();
            if (StringUtils.isNotEmpty(connection.toString())) {
                String sql = "select password from users where  username = " + "'" + username + "'";
                ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
                while (resultSet.next()) {
                    psd = resultSet.getString("password");
                }

            }
        } catch (Exception e) {
          throw  new MyException(ExceptionEnum.EXCEPTION_RUN_ERROR);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return psd;
    }
}
