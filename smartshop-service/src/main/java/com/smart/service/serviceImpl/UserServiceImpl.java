package com.smart.service.serviceImpl;

import com.smart.mapper.UserInfoMapper;
import com.smart.pojo.User;
import com.smart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Integer registerUser(User user) {
        return userInfoMapper.insertUser(user);
    }

    @Override
    public List<User> getUserList() {
        return userInfoMapper.selectAll();
    }
}
