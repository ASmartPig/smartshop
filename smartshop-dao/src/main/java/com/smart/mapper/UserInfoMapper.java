package com.smart.mapper;

import com.smart.pojo.User;

import java.util.List;

public interface UserInfoMapper {
    List<User> selectAll();

    int insertUser(User user);

}
