package com.smart.mapper;

import com.smart.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
    List<User> selectAll();

    int insertUser(User user);

    User selectUserByUserAccount(@Param("account") String account);




}
