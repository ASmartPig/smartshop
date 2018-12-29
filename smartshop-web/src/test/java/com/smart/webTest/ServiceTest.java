package com.smart.webTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")
public class ServiceTest {

    @Test
    public void test() {
        log.info("3423423423");
//        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:spring-mvc.xml");
//        System.out.println(act.containsBean("userInfoController"));
    }
}
