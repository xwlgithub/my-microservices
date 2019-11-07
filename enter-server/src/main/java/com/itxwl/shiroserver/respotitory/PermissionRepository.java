package com.itxwl.shiroserver.respotitory;

import com.itxwl.shiroserver.entiry.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,String> {
    @Query
    List<Permission> findPermissionByPid(String pid);

}
