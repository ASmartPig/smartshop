package com.smart.tablestore;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.CreateIndexRequest;
import com.alicloud.openservices.tablestore.model.IndexMeta;

import com.alicloud.openservices.tablestore.model.search.*;
import com.alicloud.openservices.tablestore.model.search.query.BoolQuery;
import com.alicloud.openservices.tablestore.model.search.query.MatchAllQuery;
import com.alicloud.openservices.tablestore.model.search.query.MatchQuery;
import com.alicloud.openservices.tablestore.model.search.query.RangeQuery;
import com.alicloud.openservices.tablestore.model.search.sort.FieldSort;
import com.alicloud.openservices.tablestore.model.search.sort.Sort;
import com.alicloud.openservices.tablestore.model.search.sort.SortOrder;
import com.smart.configuration.ClientConfig;
import javafx.animation.Timeline;

import java.util.Arrays;
import java.util.List;

import static sun.misc.JarIndex.INDEX_NAME;

/**
 * @Description
 * @Author liaoze
 * @Date 2019/6/24 上午6:37
 **/
public class IndexOperation {

  static  ClientConfig clientConfig = new ClientConfig();

    public static void main(String[] args) {
       // System.out.println(listSearchIndex(clientConfig.getClient()));
        matchQuery(clientConfig.getClient(),"35ad2d08-c3f8-4761-9637-bedd849860c6");
        clientConfig.getClient().shutdown();

    }


    /**
     * 添加二级索引添加失败了
     */
    public static void initTimelineStore(){
        IndexMeta indexMeta = new IndexMeta("timeline__search"); // 要创建的索引表名称。
        indexMeta.addPrimaryKeyColumn("timelineid"); // 为索引表添加主键列。
        indexMeta.addPrimaryKeyColumn("sender_id"); // 为索引表添加主键列。
        indexMeta.addPrimaryKeyColumn("receiver_id"); // 为索引表添加主键列。
        indexMeta.addDefinedColumn("message_id"); // 为索引表添加属性列。
        indexMeta.addDefinedColumn("is_group_message");
        CreateIndexRequest request = new CreateIndexRequest("message", indexMeta, true);
        clientConfig.getClient().createIndex(request);
    }


    /**
     * 创建索引表使用这种方式创建成功
     * @param client
     */
    private static void createSearchIdIndex(SyncClient client) {
        CreateSearchIndexRequest request = new CreateSearchIndexRequest();
        request.setTableName("message"); // 设置表名
        request.setIndexName("timeline_search"); // 设置索引名
        IndexSchema indexSchema = new IndexSchema();
        indexSchema.setFieldSchemas(Arrays.asList(
                new FieldSchema("timelineid", FieldType.KEYWORD) // 设置字段名、类型
                        .setIndex(true) // 设置开启索引
                        .setEnableSortAndAgg(true), // 设置开启排序和统计功能
                new FieldSchema("sender_id", FieldType.KEYWORD)
                        .setIndex(true)
                        .setEnableSortAndAgg(true),
                new FieldSchema("receiver_id", FieldType.KEYWORD)
                        .setIndex(true)
                        .setEnableSortAndAgg(true),
                new FieldSchema("message_id", FieldType.KEYWORD)
                        .setIndex(true)
                        .setEnableSortAndAgg(true),
                new FieldSchema("is_group_message",FieldType.KEYWORD)
                .setIndex(true)));
        request.setIndexSchema(indexSchema);
        client.createSearchIndex(request); // 调用client创建SearchIndex
    }

    /**
     * 查看表下面有哪些索引
     * @param client
     * @return
     */
    private static List<SearchIndexInfo> listSearchIndex(SyncClient client) {
        ListSearchIndexRequest request = new ListSearchIndexRequest();
        request.setTableName("message"); // 设置表名
        return client.listSearchIndex(request).getIndexInfos(); // 返回表下所有SearchIndex
    }

    /**
     * 列出具体索引信息
     * @param client
     * @return
     */
    private static DescribeSearchIndexResponse describeSearchIndex(SyncClient client) {
        DescribeSearchIndexRequest request = new DescribeSearchIndexRequest();
        request.setTableName("message"); // 设置表名
        request.setIndexName("timeline_search"); // 设置索引名
        DescribeSearchIndexResponse response = client.describeSearchIndex(request);
        System.out.println(response.jsonize()); // 输出response的详细信息
        return response;
    }

    /**
     * 删除索引
     * @param client
     */
    private static void deleteSearchIndex(SyncClient client) {
        DeleteSearchIndexRequest request = new DeleteSearchIndexRequest();
        request.setTableName("message"); // 设置表名
        request.setIndexName("timeline_id_search"); // 设置索引名
        client.deleteSearchIndex(request); // 调用client删除对应的多元索引
    }


    /**
     * 通过MatchAllQuery查询表中数据的总行数
     * @param client
     */
    private static void matchAllQuery(SyncClient client) {
        SearchQuery searchQuery = new SearchQuery();

        /**
         * 设置查询类型为MatchAllQuery
         */
        searchQuery.setQuery(new MatchAllQuery());

        /**
         * MatchAllQuery结果中的TotalCount可以表示表中数据的总行数(数据量很大时为估计值)，
         * 如果只为了取行数，但不需要具体数据，可以设置limit=0，即不返回任意一行数据。
         */
        searchQuery.setLimit(1);
        SearchRequest searchRequest = new SearchRequest("message", "timeline_id_search", searchQuery);

        /**
         * 设置返回命中的总行数。
         */
        SearchResponse resp = client.search(searchRequest);
        /**
         * 判断返回的结果是否是完整的，当isAllSuccess为false时，代表可能有部分节点查询失败，返回的是部分数据
         */
        if (!resp.isAllSuccess()) {
            System.out.println("NotAllSuccess!");
        }
        System.out.println("IsAllSuccess: " + resp.isAllSuccess());
        System.out.println("TotalCount: " + resp.getTotalCount()); // 总行数
        System.out.println(resp.getRequestId());
        System.out.println(resp.getRows());
    }

    /**
     * 查询表中Col_Keyword这一列的值能够匹配"hangzhou"的数据，返回匹配到的总行数和一些匹配成功的行。
     * 查找聊天记录
     * @param client
     */
    private static void matchQuery(SyncClient client, String timelineid) {
        SearchQuery searchQuery = new SearchQuery();
        MatchQuery matchQuery = new MatchQuery(); // 设置查询类型为MatchQuery
        matchQuery.setFieldName("timelineid"); // 设置要匹配的字段
        matchQuery.setText(timelineid); // 设置要匹配的值
        searchQuery.setQuery(matchQuery);
        searchQuery.setOffset(0); // 设置offset为0
        searchQuery.setLimit(10); // 设置limit为20，表示最多返回20行数据
        SearchRequest searchRequest = new SearchRequest("message", "timeline_search", searchQuery);
        SearchResponse resp = client.search(searchRequest);
        System.out.println("TotalCount: " + resp.getTotalCount());
        System.out.println("Row: " + resp.getRows()); // 不设置columnsToGet，默认只返回主键

        SearchRequest.ColumnsToGet columnsToGet = new SearchRequest.ColumnsToGet();
        columnsToGet.setReturnAll(true); // 设置返回所有列
        searchRequest.setColumnsToGet(columnsToGet);

        resp = client.search(searchRequest);
        System.out.println("TotalCount: " + resp.getTotalCount()); // 匹配到的总行数，非返回行数
        System.out.println("Row: " + resp.getRows());
    }


    /**
     * 通过BoolQuery进行复合条件查询。
     * 查询timelineid
     * @param client
     */
    public static void boolQuery(SyncClient client) {
        /**
         * 查询条件一：RangeQuery，Col_Long这一列的值要大于3
         */
        MatchQuery matchQuery1 = new MatchQuery(); // 设置查询类型为MatchQuery
        matchQuery1.setFieldName("sender_id"); // 设置要匹配的字段
        matchQuery1.setText("liaoze"); // 设置要匹配的值

        /**
         * 查询条件二：MatchQuery，Col_Keyword这一列的值要匹配"hangzhou"
         */
        MatchQuery matchQuery2 = new MatchQuery(); // 设置查询类型为MatchQuery
        matchQuery2.setFieldName("receiver_id"); // 设置要匹配的字段
        matchQuery2.setText("water"); // 设置要匹配的值

        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setOffset(0); // 设置offset为0
        searchQuery.setLimit(1); // 设置limit为20，表示最多返回20行数据
        {
            /**
             * 构造一个BoolQuery，设置查询条件是必须同时满足"条件一"和"条件二"
             */
            BoolQuery boolQuery = new BoolQuery();
            boolQuery.setMustQueries(Arrays.asList(matchQuery1, matchQuery2));
            searchQuery.setQuery(boolQuery);
            searchQuery.setGetTotalCount(true);
            SearchRequest searchRequest = new SearchRequest("message", "timeline_id_search", searchQuery);
            SearchResponse resp = client.search(searchRequest);
            System.out.println("TotalCount: " + resp.getTotalCount()); // 匹配到的总行数，非返回行数
            System.out.println("Row: " + resp.getRows());
        }

//        {
//            /**
//             * 构造一个BoolQuery，设置查询条件是至少满足"条件一"和"条件二"中的一个条件
//             */
//            BoolQuery boolQuery = new BoolQuery();
//            boolQuery.setShouldQueries(Arrays.asList(rangeQuery, matchQuery));
//            boolQuery.setMinimumShouldMatch(1); // 设置最少满足一个条件
//            searchQuery.setQuery(boolQuery);
//            searchQuery.setGetTotalCount(true);
//            SearchRequest searchRequest = new SearchRequest(TABLE_NAME, INDEX_NAME, searchQuery);
//            SearchResponse resp = client.search(searchRequest);
//            System.out.println("TotalCount: " + resp.getTotalCount()); // 匹配到的总行数，非返回行数
//            System.out.println("Row: " + resp.getRows());
//        }
    }

    /**
     * 范围查找（大概知道，没研究透）
     * 查询表中Col_Long这一列大于3的数据，结果按照Col_Long这一列的值逆序排序。
     * @param client
     */

    private static void rangeQuery(SyncClient client) {
        SearchQuery searchQuery = new SearchQuery();
        RangeQuery rangeQuery = new RangeQuery(); // 设置查询类型为RangeQuery
        rangeQuery.setFieldName("sequence_id");  // 设置针对哪个字段
        rangeQuery.greaterThan(ColumnValue.fromLong(222l));  // 设置该字段的范围条件，大于3
        searchQuery.setGetTotalCount(true);
        searchQuery.setQuery(rangeQuery);

//        // 设置按照Col_Long这一列逆序排序
//        FieldSort fieldSort = new FieldSort("create_time");
//        fieldSort.setOrder(SortOrder.DESC);
//        searchQuery.setSort(new Sort(Arrays.asList((Sort.Sorter)fieldSort)));

        SearchRequest searchRequest = new SearchRequest("message", "timeline_search", searchQuery);

        SearchResponse resp = client.search(searchRequest);
        System.out.println("TotalCount: " + resp.getTotalCount()); // 匹配到的总行数，非返回行数
        System.out.println("Row: " + resp.getRows());
    }




}
