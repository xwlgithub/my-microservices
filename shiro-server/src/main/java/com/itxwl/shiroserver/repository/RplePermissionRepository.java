package com.itxwl.shiroserver.repository;

import com.itxwl.shiroserver.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RplePermissionRepository extends JpaRepository<RolePermission,Long> {
    RolePermission findRolePermissionById(Long id);
}
