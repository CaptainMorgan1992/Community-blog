package com.communityblog.repository;

import com.communityblog.model.Blogpost;
import com.communityblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogpostRepository extends JpaRepository<Blogpost, Long> {

}
