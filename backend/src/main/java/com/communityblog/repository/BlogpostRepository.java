package com.communityblog.repository;

import com.communityblog.model.Blogpost;
import com.communityblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogpostRepository extends JpaRepository<Blogpost, Long> {

    List<Blogpost> findByBlogpostUsername(String username);


}
