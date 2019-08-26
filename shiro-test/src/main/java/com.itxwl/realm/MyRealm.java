package com.itxwl.realm;

import com.itxwl.utils.DataSourceMysql;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

//realm认证
@SuppressWarnings("all")
public class MyRealm extends AuthorizingRealm {
    //权限认证
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

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            //从主体传过来的认证信息中获取用户名
        String  username = (String)authenticationToken.getPrincipal();
            //通过用户名到数据库获取凭证
       String password= getPasswordByUserName(username);
       if (StringUtils.isNotEmpty(password)){
           //密码加密
           //Md5Hash md5Hash=new Md5Hash(password,username);
           //System.out.println("密码加盐加密"+md5Hash.toString());
           SimpleAuthenticationInfo simpleAuthenticationInfo=
                   new SimpleAuthenticationInfo(username,password,"customRealm");
           //密码和用户名同时加盐
           simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
           return simpleAuthenticationInfo;
       }
       return null;
    }

    private String getPasswordByUserName(String username)  {
        Connection dataSource=null;
        String psd=null;
        try {
             dataSource = DataSourceMysql.getDataSource();
            if (StringUtils.isNotEmpty(dataSource.toString())){
                String sql="select password from users where  username = "+"'"+username+"'";
                ResultSet resultSet = dataSource.prepareStatement(sql).executeQuery();
                while (resultSet.next()){
                    psd=resultSet.getString("password");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                dataSource.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return psd;
    }

    public static void main(String[] args) {
        //Md5Hash md5Hash=new Md5Hash("123","xwl");

        //String s = md5Hash.toString();
        //System.out.println(s);
    }
}
