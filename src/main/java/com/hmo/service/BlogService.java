package com.hmo.service;

import com.hmo.common.MongoPage;
import com.hmo.entity.Blog;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface BlogService {
    Blog create(Blog blog);
    void delete(String Id);
    List<Blog> findAll();
    Blog updata(Blog blog);
    MongoPage nextPage(Integer pageSize, Integer pageNum, String lastId);
}
