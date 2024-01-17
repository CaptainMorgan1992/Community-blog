package com.communityblog.repository;

import com.communityblog.model.Blogpost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogpostRepository extends JpaRepository<Blogpost, Integer> {
}
