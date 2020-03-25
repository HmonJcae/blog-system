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
    @ApiImplicitParam(name = "user",value = "userName,passWord,/n用户名,密码")
    private CommonResult addUser(@RequestBody User user){
        //@RequestBody String userName,@RequestBody String passWord
        User u = userService.add(user.getUserName(),user.getPassWord());
        if (u==null)
            return CommonResult.failed("用户已存在");
        return CommonResult.success(u);
    }

    @ApiOperation("修改用户")
    @RequestMapping(value ="/updata",method = RequestMethod.POST)
    @ApiImplicitParam(name = "user",value = "passWord,/n密码")
    private CommonResult updata(@RequestBody User user){
        User u = userService.updata(user);
        return CommonResult.success(u);
    }

    @ApiOperation("用户登录")
    @RequestMapping(value ="/login",method = RequestMethod.POST)
    @ApiImplicitParam(name = "map",value = "userName,passWord,/n用户名,密码")
    private CommonResult login(@RequestBody Map map){
        String token = userService.login(map.get("userName").toString(),map.get("passWord").toString());
        if (token==null)
            return CommonResult.failed("");
        Map<String,String> map1=new HashMap<>();
        map1.put("head",head);
        map1.put("header",token);
        return CommonResult.success(map1);
    }

}
