package com.example.user.userdomain.repository;

import com.example.user.userdomain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByNickname(String nickname);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);
}
