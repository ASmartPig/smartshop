package com.smart.tablestore;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.*;
import com.smart.configuration.ClientConfig;
import com.smart.model.MessageDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author liaoze
 * @Date 2019/6/23 下午3:52
 **/
@Component
public class InsertRowOperation {

    private static final Logger logger = LoggerFactory.getLogger(InsertRowOperation.class);




    public  void putRow(SyncClient client, MessageDo messageDo) {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();



        // 第一列的值为 hash(receive_id)前4位
        primaryKeyBuilder.addPrimaryKeyColumn("timelineid",PrimaryKeyValue.fromString(messageDo.getTimeline_id()));

        // 第二列的值为接收方ID
        primaryKeyBuilder.addPrimaryKeyColumn("sender_id", PrimaryKeyValue.fromString(messageDo.getSender_id()));
        primaryKeyBuilder.addPrimaryKeyColumn("receiver_id", PrimaryKeyValue.fromString(messageDo.getReceiver_id()));

        // 第三列是消息ID，主键递增列，这个值是TableStore产生的，用户在这里不需要填入真实值，只需要一个占位符：AUTO_INCREMENT 即可。
        primaryKeyBuilder.addPrimaryKeyColumn("sequence_id", PrimaryKeyValue.AUTO_INCREMENT);
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        RowPutChange rowPutChange = new RowPutChange("message", primaryKey);

        // 这里设置返回类型为RT_PK，意思是在返回结果中包含PK列的值。如果不设置ReturnType，默认不返回。
        //rowPutChange.setReturnType(ReturnType.RT_PK);

        //加入属性列，消息内容
        rowPutChange.addColumn(new Column("message_id", ColumnValue.fromString(messageDo.getMessage_id())));
        rowPutChange.addColumn(new Column("message_type", ColumnValue.fromString(messageDo.getMessage_type())));
        rowPutChange.addColumn(new Column("message_content", ColumnValue.fromString(messageDo.getMessage_content())));
        rowPutChange.addColumn(new Column("is_group_message", ColumnValue.fromString(messageDo.getIs_group_message())));
        rowPutChange.addColumn(new Column("send_time", ColumnValue.fromString(messageDo.getSend_time().toString())));
        rowPutChange.addColumn(new Column("create_time", ColumnValue.fromString(messageDo.getCreate_time().toString())));
        rowPutChange.addColumn(new Column("update_time", ColumnValue.fromString(messageDo.getUpdate_time().toString())));
        PutRowResponse response = null;
        try {
            //写数据到TableStore
            response  = client.putRow(new PutRowRequest(rowPutChange));
        }catch (Exception e){
            logger.error("错误消息为", e);
        }


//        // 打印出返回的PK列
//        Row returnRow = response.getRow();
//        if (returnRow != null) {
//            System.out.println("PrimaryKey:" + returnRow.getPrimaryKey().toString());
//        }
//
//        // 打印出消耗的CU
//        CapacityUnit  cu = response.getConsumedCapacity().getCapacityUnit();
//        System.out.println("Read CapacityUnit:" + cu.getReadCapacityUnit());
//        System.out.println("Write CapacityUnit:" + cu.getWriteCapacityUnit());


    }

}
