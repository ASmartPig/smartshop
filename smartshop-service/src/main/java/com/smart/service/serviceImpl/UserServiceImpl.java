package com.smart.service.serviceImpl;

import com.smart.mapper.UserInfoMapper;
import com.smart.pojo.User;
import com.smart.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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

    @Override
    public boolean isExistAccount(String account) {
        logger.info("进到isExistAccount method中 account:{}",account);
        if(Objects.nonNull((userInfoMapper.selectUserByUserAccount(account)))){
            return true;
        }
        return false;
    }

    @Override
    public User getUserByAccount(String account) {
        return userInfoMapper.selectUserByUserAccount(account);
    }
}
