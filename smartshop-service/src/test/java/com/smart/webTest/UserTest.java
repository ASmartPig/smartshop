package com.smart.webTest;

import com.google.common.collect.Lists;
import com.smart.mapper.UserInfoMapper;
import com.smart.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")
public class UserTest {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void insertUserTest(){
//        User user = new User();
//        user.setId("1");
//        user.setAccount("12345678");
//        user.setUserName("廖泽");
//        user.setPassword("123456");
//        System.out.println(userInfoMapper.insertUser(user));
    }

    @Test
    public void selectUserTest(){
//        List<User> list = Lists.newArrayList();
//        list = userInfoMapper.selectAll();
//        list.forEach(e -> {
//                    System.out.println();
//                }
//        );
    }

    @Test
    public void test() {
//        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
//        System.out.println(act.containsBean("userServiceImpl"));
    }
}
