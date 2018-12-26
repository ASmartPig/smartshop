package com.smart.service;

import com.smart.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    /**
     * 注册用户
     * @param user
     * @return
     */
    Integer registerUser(User user);


    List<User> getUserList();

}
