package com.iteason.springboot_mybatis2.controller;

import com.iteason.springboot_mybatis2.interfaces.UpdateService;
import com.iteason.springboot_mybatis2.mapper.UserMapper;
import com.iteason.springboot_mybatis2.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
public class MyBatisController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UpdateService updateService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/get")
    @ResponseBody
    public User hello() {
        //通过mybatis查询
        User user = userMapper.selectByPrimaryKey(16);
        return user;
    }

    @RequestMapping(value="/update")
    public void update(){
        User user = new User();
        user.setUsername("arong");
        updateService.update(user);


    }
    @RequestMapping(value="/redis")
    @ResponseBody
    public String redis(){
        System.out.println("hello");
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String hello = ops.get("hello");
        ops.set("redisTest","hello Redis",10000);
        return hello;
    }

	@RequestMapping(value = "/develop")
    @ResponseBody
    public User develop() {
        //develop
        User user = userMapper.selectByPrimaryKey(16);
        return user;
    }

}