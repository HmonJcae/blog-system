package com.hmo.service.serviceImpl;

import com.hmo.dao.UserDao;
import com.hmo.entity.User;
import com.hmo.security.JwtUserDetailsService;
import com.hmo.security.util.JwtTokenUtil;
import com.hmo.service.UserService;
import jdk.nashorn.internal.objects.annotations.Where;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public User add(String username,String password) {
        User userByName = getUserByName(username);
        if (userByName !=null)
            return null;
        User user=new User();
        user.setCreateTime(new Date());
        user.setUserName(username);
        String password1 = passwordEncoder.encode(password);
        user.setPassWord(password1);
        return userDao.insert(user);
    }

    @Override
    public User updata(User user) {
        User user1 = mongoTemplate.findOne(Query.query(Criteria.where("userName").is(user.getUserName())), User.class);
        String password = passwordEncoder.encode(user.getPassWord());
        user1.setPassWord(password);
        return userDao.save(user1);
    }

    @Override
    public User getUserByName(String name) {
        User userName = mongoTemplate.findOne(Query.query(Criteria.where("userName").is(name)), User.class);
        return userName;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
//            insertLoginLog(username);
        } catch (AuthenticationException e) {
            System.out.println("登录异常:{}"+e.getMessage());
        }
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        //获取用户信息
        User admin = getUserByName(username);
        if (admin != null) {
            return new JwtUserDetailsService(admin);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
