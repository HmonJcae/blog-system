package com.hmo.dao;

import com.hmo.entity.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogDao extends MongoRepository<Blog,String> {
}
