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

    /**
     * 根据用户id获取所属角色id
     * @param userId
     * @return
     * @throws MyException
     */
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

    /**
     * 根据角色id删除
     * @param id
     * @return
     * @throws MyException
     */
    @Override
    public Boolean deleteRoleById(String id) throws MyException {
        try {
            roleRepository.delete(id);
        } catch (Exception e) {
            throw  new MyException(ExceptionEnum.DELETE_ERROR);
        }
        return true;
    }

    /**
     * 为角色分配权限
     * 待核查
     * @param roleId    角色id
     * @param permissIds    权限id
     * @return
     * @throws MyException
     */
    @Override
    @Transactional
    public Boolean authonRoleById(String roleId, String permissIds,String deteids)throws MyException {
        System.out.println(deteids.length());
        if (!StringUtils.isEmpty(deteids)){
           for (String ids : deteids.split(",")) {
               if (ids==null||ids.equals("")){
                   continue;
               }
               jdbcTemplate.update("delete from pe_role where pe_id="+ids);
           }
       }
        String[] split = permissIds.split(",");
        try {
            for (String permissId : split) {
                String sql="insert into pe_role(ro_id,pe_id)  values("+roleId+","+permissId+") on duplicate KEY update pe_id="+permissId;
                jdbcTemplate.update(sql);
            }
        } catch (DataAccessException e) {
            //throw  new MyException(ExceptionEnum.AUTH_ROLE_WITH_EXCEPTION);
            e.printStackTrace();
        }
        //如果只保留了一个菜单父栏 也删除掉
        List<String> peIds = jdbcTemplate.query("SELECT pe_id FROM pe_role WHERE ro_id ='" + roleId + "' AND pe_id in (SELECT id  FROM primission WHERE pid=0)", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("pe_id");
            }
        });
//        if (peIds.size()!=0){
//            for (String permissId : peIds) {
//                String ids = jdbcTemplate.queryForObject("SELECT GROUP_CONCAT(id SEPARATOR ',') as 'ids'  FROM primission WHERE pid='" + permissId + "'", String.class);
//                List<String> strings = jdbcTemplate.query("SELECT pe_id FROM pe_role WHERE pe_id <> '" + permissId + "'  AND ro_id='" + roleId + "' AND pe_id in (" + ids + ")", new RowMapper<String>() {
//                    @Override
//                    public String mapRow(ResultSet resultSet, int i) throws SQLException {
//                        return resultSet.getString("pe_id");
//                    }
//                });
//                if (strings.size()==0){
//                    jdbcTemplate.update("delete from pe_role where pe_id="+permissId);
//                }
//            }
//        }
        return true;
    }
}
