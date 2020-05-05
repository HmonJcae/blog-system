package com.hmo.common;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
public class MongoPage<T> {
    //每页的数量
    private Integer pageSize;
    //总共的条数
    private Long total;
    //总共的页数
    private Integer pages;
    //实体类集合
    private List<T> list;
    //最后一条ID
    private String lastId;
    //第几页
    private Integer pageNum;


    private final int FIRST_PAGE_NUM = 1;

    @Autowired
    private MongoTemplate template;

    /**
     * @param query       查询语句
     * @param pageSize    分页条数
     * @param entityClass 实体类
     * @param pageNum     当前页
     * @param lastId      分页的最后一条
     * @return
     */
    public MongoPage restPage(Query query, Integer pageSize, Class<T> entityClass, Integer pageNum, String lastId, String orderKey) {
        //分页逻辑
        long total = template.count(query, entityClass);
        final Integer pages = (int) Math.ceil(total / (double) pageSize);
        if (pageNum == null || pageNum <= 0 || pageNum > pages) {
            pageNum = FIRST_PAGE_NUM;
        }
        final Criteria criteria = new Criteria();
        if (StringUtils.isEmpty(lastId)) {
            int skip = pageSize * (pageNum - 1);
            query.skip(skip).limit(pageSize);
        } else {
            if (pageNum != FIRST_PAGE_NUM) {
                criteria.and(orderKey).gt(new ObjectId(lastId));
            }
            query.limit(pageSize);
        }
        final List<T> entityList = template
                .find(query.addCriteria(criteria).with(Sort.by(Sort.Direction.ASC, orderKey)), entityClass);
        MongoPage<T> mongoPage = new MongoPage<>();
        mongoPage.setTotal(total);
        mongoPage.setPages(pages);
        mongoPage.setPageSize(pageSize);
        mongoPage.setList(entityList);
        return mongoPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }
}
