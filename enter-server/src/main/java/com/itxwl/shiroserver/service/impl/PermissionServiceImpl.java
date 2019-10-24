package com.itxwl.shiroserver.service.impl;

import com.itxwl.shiroserver.dto.PermonDto;
import com.itxwl.shiroserver.entiry.Permission;
import com.itxwl.shiroserver.entiry.PermissionApi;
import com.itxwl.shiroserver.entiry.PermissionPoint;
import com.itxwl.shiroserver.respotitory.PermissionRepository;
import com.itxwl.shiroserver.service.PermissionService;
import com.itxwl.shiroserver.utils.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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

    private List<String> findPermissionByPid() {
        return jdbcTemplate.query("select id from primission where pid =" + 0, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("id");
            }
        });
    }
}
