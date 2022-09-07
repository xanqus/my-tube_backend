package com.xaqnus.my_tube_backend.user.dao;

import com.xaqnus.my_tube_backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);


    User findByEmail(String email);
}
