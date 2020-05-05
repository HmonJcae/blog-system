package com.hmo.service;

import com.hmo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface UserService {

    User add(String username,String password);
    User updata(User user);
    User getUserByName(String name);
    Map login(String username, String password);
    UserDetails loadUserByUsername(String username);
}
