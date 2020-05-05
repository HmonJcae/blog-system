package com.hmo.controller;

import com.hmo.common.CommonResult;
import com.hmo.entity.User;
import com.hmo.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${jwt.tokenHead}")
    private String head;

    @ApiOperation("用户注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    private CommonResult addUser(@RequestBody User user){
        //@RequestBody String userName,@RequestBody String passWord
        User u = userService.add(user.getUserName(),user.getPassWord());
        if (u==null)
            return CommonResult.failed("用户已存在");
        return CommonResult.success(u);
    }

    @ApiOperation("修改用户")
    @RequestMapping(value ="/updata",method = RequestMethod.PUT)
    private CommonResult updata(@RequestBody User user){
        User u = userService.updata(user);
        return CommonResult.success(u);
    }

    @ApiOperation("用户登录")
    @RequestMapping(value ="/login",method = RequestMethod.POST)
    private CommonResult login(@RequestBody User user){
        Map<String,String> map = userService.login(user.getUserName(),user.getPassWord());
        String s = map.get("header");
        if (s==null)
            return CommonResult.failed(map.get("0"));
        map.put("head",head);
        return CommonResult.success(map);
    }

}
