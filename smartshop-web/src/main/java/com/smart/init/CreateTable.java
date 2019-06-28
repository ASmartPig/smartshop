package com.smart.init;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.*;
import com.smart.configuration.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 * @Author liaoze
 * @Date 2019/6/23 下午1:58
 **/
public class CreateTable {

    @Autowired
    private ClientConfig clientConfig;


    public static void main(String[] args) {
        //ClientConfig.getClient();

    }
    /**
     *
     * @param client
     */
    private static void createMessageTable(SyncClient client) {
        TableMeta tableMeta = new TableMeta("message");

        // 第一列为分区建
        tableMeta.addPrimaryKeyColumn(new PrimaryKeySchema("partition_key", PrimaryKeyType.STRING));

        // 第二列为接收方ID
        tableMeta.addPrimaryKeyColumn(new PrimaryKeySchema("receive_id", PrimaryKeyType.STRING));

        // 第三列为消息ID，自动自增列，类型为INTEGER，属性为PKO_AUTO_INCREMENT
        tableMeta.addPrimaryKeyColumn(new PrimaryKeySchema("message_id", PrimaryKeyType.INTEGER, PrimaryKeyOption.AUTO_INCREMENT));

        int timeToLive = -1;  // 永不过期，也可以设置数据有效期，过期了会自动删除
        int maxVersions = 1;  // 只保存一个版本，目前支持多版本

        TableOptions tableOptions = new TableOptions(timeToLive, maxVersions);

        CreateTableRequest request = new CreateTableRequest(tableMeta, tableOptions);

        client.createTable(request);
    }

}
