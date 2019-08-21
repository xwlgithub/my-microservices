package com.itxwl.shiroserver.respotitory;

import com.itxwl.shiroserver.entiry.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
