package com.hmo.controller;

import com.hmo.common.CommonResult;
import com.hmo.common.MongoPage;
import com.hmo.entity.Blog;
import com.hmo.service.BlogService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/blog")
@ResponseBody
public class BlogController {
    @Autowired
    private BlogService blogService;

    @ApiOperation("上传博客")
    @RequestMapping(value = "/push",method = RequestMethod.POST)
    @ApiImplicitParam(name = "blog",value = "不需要时间")
    private CommonResult addBlog(@RequestBody Blog blog){
        Blog blog1 = blogService.create(blog);
        return CommonResult.success(blog1);
    }

    @ApiOperation("未实现//删除博客")
    @RequestMapping(value = "/del",method = RequestMethod.GET)
    private CommonResult del(String id){
        blogService.delete(id);
        return CommonResult.success("");
    }

    @ApiOperation("检索所有博客")
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    private CommonResult findadd(){
        List<Blog> all = blogService.findAll();
        return CommonResult.success(all);
    }

    @ApiOperation("分页查询")
    @RequestMapping(value = "/nextOrSkip",method = RequestMethod.GET)
    @ApiImplicitParam(name = "map",value = "pageSize,pageNum,lastId,页条数,页码,页最后一条Id",defaultValue = "pageSize=5,pageNum=1")
    private CommonResult nextOrSkip(@RequestBody Map map){
        Integer pageSize = (Integer) map.get("pageSize");
        if (pageSize==null)
            pageSize=5;
        Object lastId1 = map.get("lastId");
        String lastId=null;
        if (lastId1!=null)
            lastId = lastId1.toString();
        Integer pageNum = (Integer) map.get("pageNum");

        MongoPage mongoPage = blogService.nextPage(pageSize,pageNum,lastId);
        return CommonResult.success(mongoPage);
    }

}
