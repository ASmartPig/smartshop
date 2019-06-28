package com.smart.init;

import com.alicloud.openservices.tablestore.ClientConfiguration;
import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.timeline2.TimelineStoreFactory;
import com.alicloud.openservices.tablestore.timeline2.core.TimelineStoreFactoryImpl;

/**
 * @Description
 * @Author liaoze
 * @Date 2019/6/25 下午3:00
 **/
public class InitFactory {

    final static String endPoint = "https://liaoze-test.cn-hangzhou.ots.aliyuncs.com";
    final static String accessKeyId = "LTAI2DSrwJLTwprM";
    final static String accessKeySecret = "LI5tsmd5WDUH6QN2q0j5blpIBi5hrD";
    final static String instanceName = "liaoze-test";

    ClientConfiguration clientConfiguration = new ClientConfiguration();

    SyncClient client = new SyncClient(endPoint, accessKeyId, accessKeySecret, instanceName,clientConfiguration);;


    TimelineStoreFactory factory = new TimelineStoreFactoryImpl(client);

    public TimelineStoreFactory getFactory() {
        return factory;
    }


}
