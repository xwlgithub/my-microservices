package com.itxwl.shiroserver.service.impl;

import com.itxwl.shiroserver.dto.PermissChildrenDto;
import com.itxwl.shiroserver.dto.PermissDto;
import com.itxwl.shiroserver.dto.PermonDto;
import com.itxwl.shiroserver.entiry.Permission;
import com.itxwl.shiroserver.entiry.PermissionApi;
import com.itxwl.shiroserver.entiry.PermissionPoint;
import com.itxwl.shiroserver.respotitory.PermissionRepository;
import com.itxwl.shiroserver.service.PermissionService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private PermissionRepository permissionRepository;
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<PermonDto> findAllPerion() {
        List<PermonDto> list = new LinkedList<>();
        List<String> ids = findPermissionByPid();
        for (String id : ids) {
            PermonDto permonDto = new PermonDto();
            permonDto.setPermonName(jdbcTemplate.queryForObject("select pe_name from primission where id=" + id, String.class));
            List<Permission> permissions = permissionRepository.findPermissionByPid(id);
            permonDto.setPermissionList(permissions);
            list.add(permonDto);
        }
        return list;
    }
    //树形结构
    @SuppressWarnings("all")
    @Override
    public List<PermissDto> findAllPerions() {
        List<PermissDto> list = new LinkedList<>();
        List<String> ids = findPermissionByPid();
        for (String id : ids) {
            PermissDto permonDto = new PermissDto();
            permonDto.setId(id);
            permonDto.setLabel(jdbcTemplate.queryForObject("select pe_name from primission where id=" + id, String.class));
            List<PermissChildrenDto> permissChildrenDtos = jdbcTemplate.query("select id,pe_name from primission where pid=" + id, new RowMapper<PermissChildrenDto>() {
                @Override
                public PermissChildrenDto mapRow(ResultSet resultSet, int i) throws SQLException {
                    PermissChildrenDto permissChildrenDto = new PermissChildrenDto();
                    permissChildrenDto.setId(resultSet.getString("id"));
                    permissChildrenDto.setLabel(resultSet.getString("pe_name"));
                    return permissChildrenDto;
                }
            });
            permonDto.setChildren(permissChildrenDtos);
            list.add(permonDto);
        }
        return list;
    }

    /**
     * 根据角色id获取权限信息
     * @param roleId
     * @return
     */
    @Override
    @SuppressWarnings("all")
    public List<PermissChildrenDto> findRoleByPersId(String roleId) {
        List<PermissChildrenDto> list=new LinkedList<>();
        //获取角色关联权限ID 多
        String sql="select pe_id from pe_role where ro_id="+roleId;
        List<String> peIds = jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("pe_id");
            }
        });
        if(peIds.size()==0){
            return list;
        }
        //获取父节点菜单--筛选Pid
        String parentsIds = StringUtils.join(findPermissionByPid(), ",");
        Iterator<String> iterator = peIds.iterator();
        while (iterator.hasNext()){
            String peId = iterator.next();
            if (parentsIds.contains(peId)){
                iterator.remove();
            }else {
                PermissChildrenDto permissChildrenDto=new PermissChildrenDto();
                jdbcTemplate.query("select id,pe_name from primission where id=" + peId, new RowMapper<PermissChildrenDto>() {
                    @Override
                    public PermissChildrenDto mapRow(ResultSet resultSet, int i) throws SQLException {
                        permissChildrenDto.setId(resultSet.getString("id"));
                        permissChildrenDto.setLabel(resultSet.getString("pe_name"));
                        return permissChildrenDto;
                    }
                });
                list.add(permissChildrenDto);

            }
        }
        return list;
    }

    /**
     * 根据权限id获取所有按钮
     * @param id
     * @return
     */
    @Override
    public List<PermissionPoint> findPointById(String id) {
        return jdbcTemplate.query("select * from pe_permission_point where permission_id =" + id,
                new RowMapper<PermissionPoint>() {
                    @Override
                    public PermissionPoint mapRow(ResultSet resultSet, int i) throws SQLException {
                        PermissionPoint permissionPoint = new PermissionPoint();
                        permissionPoint.setId(resultSet.getString("id"));
                        permissionPoint.setPermissionId(resultSet.getString("permission_id"));
                        permissionPoint.setPointCode(resultSet.getString("point_code"));
                        permissionPoint.setPointName(resultSet.getString("point_name"));
                        return permissionPoint;
                    }
                });
    }

    /**
     * 根据权限id获取该菜单所属API
     * @param id
     * @return
     */
    @Override
    public PermissionApi findApiById(String id) {
        List<PermissionApi> query = jdbcTemplate.query
                ("select * from pe_permission_api where permission_id=" + id, new RowMapper<PermissionApi>() {
                    @Override
                    public PermissionApi mapRow(ResultSet resultSet, int i) throws SQLException {
                        PermissionApi permissionApi = new PermissionApi();
                        permissionApi.setId(resultSet.getString("id"));
                        permissionApi.setApiMethod(resultSet.getString("api_method"));
                        permissionApi.setApiUrl(resultSet.getString("api_url"));
                        permissionApi.setPermissionId(resultSet.getString("permission_id"));
                        return permissionApi;
                    }
                });
        if (query.size()==0){
            return null;
        }
        return query.get(0);
    }

    /**
     * 获得所有父节点菜单
     * @return
     */
    private List<String> findPermissionByPid() {
        return jdbcTemplate.query("select id from primission where pid =" + 0, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("id");
            }
        });
    }
}
