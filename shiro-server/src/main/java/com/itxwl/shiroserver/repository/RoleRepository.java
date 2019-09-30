package com.itxwl.shiroserver.repository;

import com.itxwl.shiroserver.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<UserRoles,Long> {
    List<UserRoles> findAllById(Long id);
}
