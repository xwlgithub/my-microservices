package com.itxwl.shiroserver.service.impl;

import com.itxwl.shiroserver.dto.PeRoleIdDto;
import com.itxwl.shiroserver.dto.RolePointDto;
import com.itxwl.shiroserver.dto.ZDto;
import com.itxwl.shiroserver.entiry.Permission;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    @Autowired
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
        String sql = "";
        sql = "select * from role where role_name <> '薛' order by create_time desc";
        List<Role> list = jdbcTemplate.query(sql, new RowMapper<Role>() {
            @Override
            public Role mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Role(resultSet.getString("id"),
                        resultSet.getString("role_name"),
                        resultSet.getString("role_remark"), resultSet.getDate("create_time"),
                        null);
            }
        });
        return list;
    }

    /**
     * 根据用户id获取所属角色id
     *
     * @param userId
     * @return
     * @throws MyException
     */
    @Override
    public List<String> findRolesByUserId(String userId) throws MyException {
        List<String> list = null;
        try {
            String sql = "select role_id from ro_user where user_id = " + userId;
            list = jdbcTemplate.query(sql, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("role_id");
                }
            });
        } catch (Exception e) {
            throw new MyException(ExceptionEnum.EXCEPTION_RUN_ERROR);
        }
        return list;
    }

    /**
     * 根据角色id删除
     *
     * @param id
     * @return
     * @throws MyException
     */
    @Transactional
    @Override
    public Boolean deleteRoleById(String id) throws MyException {
        try {
            roleRepository.delete(id);
        } catch (Exception e) {
            throw new MyException(ExceptionEnum.DELETE_ERROR);
        }
        return true;
    }

    /**
     * 为角色分配权限
     * 待核查
     *
     * @param roleId     角色id
     * @param permissIds 权限id
     * @param deteids    要删除的id
     * @return
     * @throws MyException
     */
    @Override
    @Transactional
    public Boolean authonRoleById(String roleId, String permissIds, String deteids) throws MyException {
        //
        System.out.println(deteids.length());
        if (!StringUtils.isEmpty(deteids)) {
            for (String ids : deteids.split(",")) {
                if (ids == null || ids.equals("")) {
                    continue;
                }
                /**
                 * 这里如果不是按钮的 一律到角色权限关系表删除
                 * 反之需要到角色按钮关系表
                 */
                String sql="select id from primission where id="+ids;
                String primissId = null;
                try {
                    primissId = jdbcTemplate.queryForObject(sql, String.class);
                } catch (EmptyResultDataAccessException e) {
                }
                //如果不是角色权限菜单表-->删除按钮表
                if (StringUtils.isEmpty(primissId)){
                    jdbcTemplate.update("delete from role_point where pointId=" + ids +" and roleId="+roleId);
                }
                jdbcTemplate.update("delete from pe_role where pe_id=" + ids);
            }
        }
        Map<String,String> map=new HashMap<>();
        List<ZDto> zDtos = jdbcTemplate.query("SELECT id,pid FROM primission WHERE pid <> 0 ", new RowMapper<ZDto>() {
            @Override
            public ZDto mapRow(ResultSet resultSet, int i) throws SQLException {
                ZDto zDto = new ZDto();
                zDto.setPid(resultSet.getString("pid"));
                zDto.setIds(resultSet.getString("id"));
                map.put(zDto.getIds(), zDto.getPid());
                return zDto;
            }
        });
        //将权限id转集合
        String[] split = permissIds.split(",");
        try {
            for (String permissId : split) {
                //判断该权限ID是否是按钮
                String sqls = "select id from pe_permission_point where id=?";
                String isPoint = null;
                try {
                    isPoint = jdbcTemplate.queryForObject(sqls, String.class, new Object[]{permissId});
                } catch (EmptyResultDataAccessException e) {

                }
                if (StringUtils.isEmpty(isPoint)) {
                        String peTypeId="";
                        if (map.get(permissId)!=null){
                            peTypeId=map.get(permissId);
                        }else {
                            peTypeId=permissId;
                        }
                        //插入角色权限关联表-
                        String sql = "insert into pe_role(ro_id,pe_id,pe_type_id)  values(" + roleId + "," + permissId + ","+peTypeId+") on duplicate KEY update pe_id=" + permissId;
                        jdbcTemplate.update(sql);
                   // }
                } else {
                    String sqld="select * from role_point where pointId="+permissId+" and roleId = "+roleId+"";
                    List<RolePointDto> rolePointDtos = jdbcTemplate.query(sqld, new RowMapper<RolePointDto>() {
                        @Override
                        public RolePointDto mapRow(ResultSet resultSet, int i) throws SQLException {
                            RolePointDto rolePointDto = new RolePointDto();
                            rolePointDto.setRoleId(resultSet.getString("roleId"));
                            rolePointDto.setRolePoint(resultSet.getString("pointId"));
                            return rolePointDto;
                        }
                    });
                    String sql = "insert into role_point(roleId,pointId) values(" + roleId + "," + permissId + ")";
                    if (rolePointDtos.size()==0 || rolePointDtos==null) {
                        jdbcTemplate.update(sql);
                    }else {
                        if (!rolePointDtos.get(0).getRoleId().equals(roleId)){
                            jdbcTemplate.update(sql);
                        }
                    }
                }
            }
        } catch (DataAccessException e) {
            //throw  new MyException(ExceptionEnum.AUTH_ROLE_WITH_EXCEPTION);
            e.printStackTrace();
        }
        //如果只有一个父菜单Id就删除
        List<String> idssss = jdbcTemplate.query("SELECT id FROM primission WHERE pid = 0 ", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("id");
            }
        });
        //父菜单权限ID
        for (String permissId : idssss) {
            String de="select ro_id from pe_role where ro_id=" + roleId + " and pe_id <> " + permissId + " and pe_type_id=" + permissId;
            List<String> id = jdbcTemplate.query
                    (de, new RowMapper<String>() {
                        @Override
                        public String mapRow(ResultSet resultSet, int i) throws SQLException {
                            return resultSet.getString("ro_id");
                        }
                    });
            if (id.size()==0 || id==null){
                jdbcTemplate.update("delete from pe_role where ro_id="+roleId +" and pe_id="+permissId);
            }
        }
        return true;
    }
}
