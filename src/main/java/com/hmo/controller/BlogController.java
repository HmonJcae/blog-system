package com.hmo.controller;

import com.hmo.common.CommonResult;
import com.hmo.common.MongoPage;
import com.hmo.entity.Blog;
import com.hmo.entity.User;
import com.hmo.service.BlogService;

import com.hmo.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/blog")
@ResponseBody
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    @ApiOperation("上传博客")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    private CommonResult addBlog(@RequestBody Blog blog){
        User userByName = userService.getUserByName(blog.getAuthorId());
        blog.setAuthorId(userByName.get_id());
        Blog blog1 = blogService.create(blog);
        return CommonResult.success(blog1);
    }

    @ApiOperation("删除博客")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    private CommonResult del(@PathVariable String id){
        blogService.delete(id);
        return CommonResult.success("");
    }

    @ApiOperation("检索所有")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    private CommonResult findAdd(){
        List<Blog> all = blogService.findAll();
        return CommonResult.success(all);
    }

    @ApiOperation("获取用户博客")
    @RequestMapping(value = "/solo/{userName}",method = RequestMethod.GET)
    private CommonResult getUserBlog(@PathVariable String userName){
        User userByName = userService.getUserByName(userName);
        List<Blog> userAll = blogService.findAllById(userByName.get_id());
        return CommonResult.success(userAll);
    }

    @ApiOperation("更新博客")
    @RequestMapping(value = "/updata",method = RequestMethod.PUT)
    private CommonResult upDataUserBlog(@RequestBody Blog blog){
        User userByName = userService.getUserByName(blog.getAuthorId());
        blog.setAuthorId(userByName.get_id());
        blog = blogService.updata(blog);
        return CommonResult.success(blog);
    }

    @ApiOperation("分页查询")
    @RequestMapping(value = "/nextOrSkip",method = RequestMethod.GET)
    @ApiImplicitParam(name = "map",value = "pageSize,pageNum,lastId,页条数,页码,页最后一条Id",defaultValue = "pageSize=5,pageNum=1")
    private CommonResult nextOrSkip(@RequestBody MongoPage mongoPage){
        Integer pageSize = mongoPage.getPageSize();
        if (pageSize==null)
            pageSize=5;
        String lastId = mongoPage.getLastId();
        Integer pageNum = mongoPage.getPageNum();
        mongoPage = blogService.nextPage(pageSize,pageNum,lastId);
        return CommonResult.success(mongoPage);
    }

}
