package com.smart.webTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ApplicationContext.xml")
public class ServiceTest {
   private static  Logger logger = LoggerFactory.getLogger(ServiceTest.class);
    @Test
    public void test() {
//        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:spring-mvc.xml");
//        System.out.println(act.containsBean("userInfoController"));

        logger.info("test........");
        logger.info("dfsdakjfaksdjfkjs");
        logger.debug("sdafkaksdjfkajsdkl.....");
    }
}
