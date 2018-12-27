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

    /**
     * 获得所有用户列表
     * @return
     */
    List<User> getUserList();


    /**
     * 是否存在这个账号
     * @param account
     * @return
     */
    boolean isExistAccount(String account);

    /**
     * 通过账号查询账号信息
     * @param account
     * @return
     */
    User getUserByAccount(String account);

}
