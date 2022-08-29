package com.xaqnus.hyper_x_backend.user.dao;

import com.xaqnus.hyper_x_backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
