package com.smart.configuration;

import com.alicloud.openservices.tablestore.SyncClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author liaoze
 * @Date 2019/6/23 下午12:05
 **/
@Configuration
public class ClientConfig {


    final static String endPoint = "https://liaoze-test.cn-hangzhou.ots.aliyuncs.com";
    final static String accessKeyId = "LTAI2DSrwJLTwprM";
    final static String accessKeySecret = "LI5tsmd5WDUH6QN2q0j5blpIBi5hrD";
    final static String instanceName = "liaoze-test";

    static SyncClient client = new SyncClient(endPoint, accessKeyId, accessKeySecret, instanceName);


    @Bean
    public SyncClient getClient() {
        return client;
    }

    public static void setClient(SyncClient client) {
        ClientConfig.client = client;
    }
}
