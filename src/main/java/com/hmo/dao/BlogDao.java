package com.hmo.dao;

import com.hmo.entity.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BlogDao extends MongoRepository<Blog,String> {
    public List<Blog> findByAuthorId(String authorId);
}
