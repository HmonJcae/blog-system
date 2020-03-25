package com.hmo.service;

import com.hmo.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    User add(String username,String password);
    User updata(User user);
    User getUserByName(String name);
    String login(String username,String password);
    UserDetails loadUserByUsername(String username);
}
