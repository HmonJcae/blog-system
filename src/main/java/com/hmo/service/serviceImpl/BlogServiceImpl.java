package com.hmo.service.serviceImpl;

import com.hmo.common.MongoPage;
import com.hmo.dao.BlogDao;
import com.hmo.entity.Blog;
import com.hmo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private MongoPage mongoPage;

    @Override
    public Blog create(Blog blog) {
        blog.setCreateTime(new Date());
        return blogDao.insert(blog);
    }

    @Override
    public void delete(String Id) {
        blogDao.deleteById(Id);
    }

    @Override
    public List<Blog> findAll() {
        return blogDao.findAll();
    }

    @Override
    public Blog updata(Blog blog) {
        return blogDao.save(blog);
    }

    @Override
    public MongoPage nextPage(Integer pageSize, Integer pageNum, String lastId) {
        Query query = new Query();
        if (pageSize.equals(null))
            pageSize=5;
        MongoPage mongoPage = this.mongoPage.restPage(query, pageSize, Blog.class, pageNum, lastId, "_id");
        return mongoPage;
    }
}
