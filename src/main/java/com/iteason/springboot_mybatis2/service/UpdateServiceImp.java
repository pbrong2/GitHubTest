package com.iteason.springboot_mybatis2.service;

import com.iteason.springboot_mybatis2.interfaces.UpdateService;
import com.iteason.springboot_mybatis2.mapper.UserMapper;
import com.iteason.springboot_mybatis2.pojo.User;
import com.iteason.springboot_mybatis2.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateServiceImp implements UpdateService {
    @Autowired
    private  UserMapper userMapper;

    @Override
    public void update(User user) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(1);

        int count = userMapper.updateByExampleSelective(user, example);

        int i = 1/0;

    }
}
