package com.daoTest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    private static Logger logger = LoggerFactory.getLogger(LogTest.class);
    @Test
    public void testSl4j(){
        logger.info("sdfsdf");
        logger.debug("sdfsdf");
    }
}
