package com.communityblog.repository;

import com.communityblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUserName(String username);

    boolean existsByEmail(String email);

    User findByUserName(String username);
}