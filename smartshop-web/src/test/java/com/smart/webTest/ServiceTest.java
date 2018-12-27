package com.smart.webTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")
public class ServiceTest {

    @Test
    public void test() {
//        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:spring-mvc.xml");
//        System.out.println(act.containsBean("userInfoController"));
    }
}
