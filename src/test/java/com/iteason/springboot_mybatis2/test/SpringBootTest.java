package com.iteason.springboot_mybatis2.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iteason.springboot_mybatis2.SpringbootMybatis2Application;
import com.iteason.springboot_mybatis2.mapper.UserMapper;
import com.iteason.springboot_mybatis2.pojo.User;
import com.iteason.springboot_mybatis2.pojo.UserExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@org.springframework.boot.test.context.SpringBootTest(classes = SpringbootMybatis2Application.class)
@WebAppConfiguration
public class SpringBootTest {


    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;
    @Test
    public void fun1(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String value = ops.get("hello");
        System.out.println(value);
    }

    @Test
    public void fun2(){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameNotEqualTo("arong");
        List<User> users = userMapper.selectByExample(userExample);
        for (User user : users) {
            System.out.println(user.getUsername());
        }

    }

    @Test
    public void fun3(){
        ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
        User user = new User();
        user.setUsername("arong");
        user.setId(1);
        ops.set("user",user,10000);
    }


    /**
     * 测试redis缓存user集合
     */

    @Test
    public void fun4(){
        Gson gson = new Gson();
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        //从redis中查询
        List<User> userList = null;
        String userListJson = null;
        try {
            userListJson = ops.get("userList");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(userListJson != null ){
            //缓存存在
            System.out.println("缓存存在");
            //使用Gson转成List<User>
            userList = gson.fromJson(userListJson, new TypeToken<List<User>>() {}.getType());
            for (User user : userList) {
                System.out.println(user.getUsername());
            }
        }else{
            //缓存不存在
            System.out.println("缓存不存在");
            UserExample example = new UserExample();
            List<User> users = userMapper.selectByExample(example);
            //存入缓存
            ops.set("userList",gson.toJson(users),100000, TimeUnit.MILLISECONDS);
            for (User user : users) {
                System.out.println(user.getUsername());
            }
        }

    }
}
