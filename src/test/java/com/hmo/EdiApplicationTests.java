package com.hmo;

import com.hmo.entity.Blog;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@SpringBootTest
class EdiApplicationTests {
	@Autowired
	private MongoTemplate template;

	@Test
	void contextLoads() {
		ObjectId id=new ObjectId("5e78823929fe1d5bb74c9d01");
		Query query=new Query();
		query.addCriteria(Criteria.where("_id").gt(id));
		query.with(Sort.by(Sort.Direction.ASC,"_id"));
		query.limit(3);

		List<Blog> blogs = template.find(query, Blog.class);
		for (Blog b:blogs)
			System.out.println(b.toString());
	}

	@Test
	void c(){
		Map map=new HashMap();
		Integer integer = (Integer) map.get("1");
		if (integer==null){
			System.out.println(1);
		}
	}

}
