package com.smart.tablestore;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.*;
import com.alicloud.openservices.tablestore.model.filter.SingleColumnValueFilter;

import java.util.Iterator;

/**
 * @Description
 * @Author liaoze
 * @Date 2019/6/24 上午11:44
 **/
public class BatchOperation {


    private static void batchGetRow(SyncClient client) {
        MultiRowQueryCriteria multiRowQueryCriteria = new MultiRowQueryCriteria("message");
        // 加入10个要读取的行
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("timelineid", PrimaryKeyValue.fromString("pk"));
        primaryKeyBuilder.addPrimaryKeyColumn("sender_id", PrimaryKeyValue.fromString("pk"));
        primaryKeyBuilder.addPrimaryKeyColumn("receiver_id", PrimaryKeyValue.fromString("pk"));
        primaryKeyBuilder.addPrimaryKeyColumn("sequence_id", PrimaryKeyValue.fromString("pk"));
        PrimaryKey primaryKey = primaryKeyBuilder.build();
        multiRowQueryCriteria.addRow(primaryKey);

        // 添加条件
        multiRowQueryCriteria.setMaxVersions(1);
        multiRowQueryCriteria.addColumnsToGet("Col0");
        multiRowQueryCriteria.addColumnsToGet("Col1");
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter("Col0",
                SingleColumnValueFilter.CompareOperator.EQUAL, ColumnValue.fromLong(0));
        singleColumnValueFilter.setPassIfMissing(false);
        multiRowQueryCriteria.setFilter(singleColumnValueFilter);

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


    private static void getRangeByIterator(SyncClient client, String startPkValue, String endPkValue) {
        RangeIteratorParameter rangeIteratorParameter = new RangeIteratorParameter("message");

        // 设置起始主键
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("timelineid", PrimaryKeyValue.fromString(startPkValue));
        rangeIteratorParameter.setInclusiveStartPrimaryKey(primaryKeyBuilder.build());

        // 设置结束主键
        primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn("timelineid", PrimaryKeyValue.fromString(endPkValue));
        rangeIteratorParameter.setExclusiveEndPrimaryKey(primaryKeyBuilder.build());

        rangeIteratorParameter.setMaxVersions(1);

        Iterator<Row> iterator = client.createRangeIterator(rangeIteratorParameter);

        System.out.println("使用Iterator进行GetRange的结果为:");
        while (iterator.hasNext()) {
            Row row = iterator.next();
            System.out.println(row);
        }
    }
}
