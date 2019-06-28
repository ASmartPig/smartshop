package com.smart.tablestore;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.*;
import com.alicloud.openservices.tablestore.model.filter.SingleColumnValueFilter;
import com.smart.model.MessageDo;

/**
 * @Description
 * @Author liaoze
 * @Date 2019/6/23 下午6:14
 **/
public class SearchRecordOperation {

    /**
     * 单行读
     * @param client
     * @param messageDo
     */
    public static void getRow(SyncClient client, MessageDo messageDo) {
        // 构造主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("sender_id", PrimaryKeyValue.fromString(messageDo.getSender_id()));
        primaryKeyBuilder.addPrimaryKeyColumn("receiver_id", PrimaryKeyValue.fromString(messageDo.getReceiver_id()));
        PrimaryKey primaryKey = primaryKeyBuilder.build();

        // 读一行
        SingleRowQueryCriteria criteria = new SingleRowQueryCriteria("message", primaryKey);
        // 设置读取最新版本
        criteria.setMaxVersions(1);
        GetRowResponse getRowResponse = client.getRow(new GetRowRequest(criteria));
        Row row = getRowResponse.getRow();

        System.out.println("读取完毕, 结果为: ");
        System.out.println(row);

        // 设置读取某些列
        criteria.addColumnsToGet("timelineid");
        getRowResponse = client.getRow(new GetRowRequest(criteria));
        row = getRowResponse.getRow();

        System.out.println("读取完毕, 结果为: ");
        System.out.println(row);
    }


    /**
     * 多读
     * @param client
     */
    public static void batchGetRow(SyncClient client) {
        MultiRowQueryCriteria multiRowQueryCriteria = new MultiRowQueryCriteria("message");
        // 加入10个要读取的行
        for (int i = 0; i < 10; i++) {
            PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
            primaryKeyBuilder.addPrimaryKeyColumn("sender_id", PrimaryKeyValue.fromString("liaoze"));
            primaryKeyBuilder.addPrimaryKeyColumn("receiver", PrimaryKeyValue.fromString("water"));
            PrimaryKey primaryKey = primaryKeyBuilder.build();
            multiRowQueryCriteria.addRow(primaryKey);
        }
        // 添加条件
        multiRowQueryCriteria.setMaxVersions(1);
        multiRowQueryCriteria.addColumnsToGet("timelineid");
        //multiRowQueryCriteria.addColumnsToGet("Col1");
//        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter("Col0",
//                SingleColumnValueFilter.CompareOperator.EQUAL, ColumnValue.fromLong(0));
//        singleColumnValueFilter.setPassIfMissing(false);
//        multiRowQueryCriteria.setFilter(singleColumnValueFilter);

        BatchGetRowRequest batchGetRowRequest = new BatchGetRowRequest();
        // batchGetRow支持读取多个表的数据, 一个multiRowQueryCriteria对应一个表的查询条件, 可以添加多个multiRowQueryCriteria.
        batchGetRowRequest.addMultiRowQueryCriteria(multiRowQueryCriteria);

        BatchGetRowResponse batchGetRowResponse = client.batchGetRow(batchGetRowRequest);

        System.out.println("是否全部成功:" + batchGetRowResponse.isAllSucceed());
        if (!batchGetRowResponse.isAllSucceed()) {
            for (BatchGetRowResponse.RowResult rowResult : batchGetRowResponse.getFailedRows()) {
                System.out.println("失败的行:" + batchGetRowRequest.getPrimaryKey(rowResult.getTableName(), rowResult.getIndex()));
                System.out.println("失败原因:" + rowResult.getError());
            }

            /**
             * 可以通过createRequestForRetry方法再构造一个请求对失败的行进行重试.这里只给出构造重试请求的部分.
             * 推荐的重试方法是使用SDK的自定义重试策略功能, 支持对batch操作的部分行错误进行重试. 设定重试策略后, 调用接口处即不需要增加重试代码.
             */
            BatchGetRowRequest retryRequest = batchGetRowRequest.createRequestForRetry(batchGetRowResponse.getFailedRows());
        }
    }


}
