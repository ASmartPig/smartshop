package com.smart.tablestore;

import com.alicloud.openservices.tablestore.SyncClient;
import com.alicloud.openservices.tablestore.model.Row;
import com.alicloud.openservices.tablestore.model.search.SearchQuery;
import com.alicloud.openservices.tablestore.model.search.SearchRequest;
import com.alicloud.openservices.tablestore.model.search.SearchResponse;
import com.alicloud.openservices.tablestore.model.search.query.BoolQuery;
import com.alicloud.openservices.tablestore.model.search.query.MatchQuery;
import com.google.common.collect.Lists;
import com.smart.configuration.ClientConfig;
import com.smart.model.MessageDo;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Author liaoze
 * @Date 2019/6/24 下午4:21
 **/
@Service
public class OperationService {

    static ClientConfig clientConfig = new ClientConfig();

    public static void main(String[] args) {
    }

    /**
     * 通过BoolQuery进行复合条件查询。
     * 查询个人聊天 timelineid
     * @param client
     */
    public String queryTimelineId(SyncClient client,String currentWechatId,String talkId ) {
        /**
         * 查询条件一：RangeQuery，Col_Long这一列的值要大于3
         */
        MatchQuery matchQuery1 = new MatchQuery(); // 设置查询类型为MatchQuery
        matchQuery1.setFieldName("sender_id"); // 设置要匹配的字段
        matchQuery1.setText(currentWechatId); // 设置要匹配的值

        /**
         * 查询条件二：MatchQuery，Col_Keyword这一列的值要匹配"hangzhou"
         */
        MatchQuery matchQuery2 = new MatchQuery(); // 设置查询类型为MatchQuery
        matchQuery2.setFieldName("receiver_id"); // 设置要匹配的字段
        matchQuery2.setText(talkId); // 设置要匹配的值

        SearchQuery searchQuery = new SearchQuery();
        searchQuery.setOffset(0); // 设置offset为0
        searchQuery.setLimit(1); // 设置limit为20，表示最多返回20行数据

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


        if (Objects.nonNull(resp) && Objects.nonNull(resp.getRows()) && !resp.getRows().isEmpty() && Objects.nonNull(resp.getRows().get(0))){
            return resp.getRows().get(0).getPrimaryKey().getPrimaryKeyColumn("timelineid").getValue().asString();
        }
        return null;

    }


    /**
     * 通过BoolQuery进行复合条件查询。
     * 查询个人聊天 timelineid
     * @param client
     */
    public  String queryGroupTimelineId(SyncClient client,String talkId) {
        SearchQuery searchQuery = new SearchQuery();
        MatchQuery matchQuery = new MatchQuery(); // 设置查询类型为MatchQuery
        matchQuery.setFieldName("receiver_id"); // 设置要匹配的字段
        matchQuery.setText(talkId); // 设置要匹配的值
        searchQuery.setQuery(matchQuery);
        searchQuery.setOffset(0); // 设置offset为0
        searchQuery.setLimit(1); // 设置limit为20，表示最多返回20行数据
        SearchRequest searchRequest = new SearchRequest("message", "timeline_id_search", searchQuery);
        SearchResponse resp = client.search(searchRequest);
        System.out.println("TotalCount: " + resp.getTotalCount());
        System.out.println("Row: " + resp.getRows()); // 不设置columnsToGet，默认只返回主键

        SearchRequest.ColumnsToGet columnsToGet = new SearchRequest.ColumnsToGet();
        columnsToGet.setReturnAll(true); // 设置返回所有列
        searchRequest.setColumnsToGet(columnsToGet);

        resp = client.search(searchRequest);
        System.out.println("TotalCount: " + resp.getTotalCount()); // 匹配到的总行数，非返回行数
        System.out.println("Row: " + resp.getRows());


        if (Objects.nonNull(resp) && Objects.nonNull(resp.getRows()) && !resp.getRows().isEmpty() && Objects.nonNull(resp.getRows().get(0))){
            return resp.getRows().get(0).getPrimaryKey().getPrimaryKeyColumn("timelineid").getValue().asString();
        }
        return null;

    }


    /**
     * 查询表中timelineid对应的聊天记录
     * 查找聊天记录
     * @param client
     */
    public List<MessageDo> matchQuery(SyncClient client, String timelineid) {
        SearchQuery searchQuery = new SearchQuery();
        // 设置查询类型为MatchQuery
        MatchQuery matchQuery = new MatchQuery();
        // 设置要匹配的字段
        matchQuery.setFieldName("timelineid");
        // 设置要匹配的值
        matchQuery.setText(timelineid);
        searchQuery.setQuery(matchQuery);
        // 设置offset为0
        searchQuery.setOffset(0);
        // 设置limit为10，表示最多返回10行数据
        searchQuery.setLimit(10);
        SearchRequest searchRequest = new SearchRequest("message", "timeline_search", searchQuery);
       // SearchResponse resp = client.search(searchRequest);


        SearchRequest.ColumnsToGet columnsToGet = new SearchRequest.ColumnsToGet();
        columnsToGet.setReturnAll(true); // 设置返回所有列
        searchRequest.setColumnsToGet(columnsToGet);

        SearchResponse resp = client.search(searchRequest);
        System.out.println("TotalCount: " + resp.getTotalCount()); // 匹配到的总行数，非返回行数
        System.out.println("Row: " + resp.getRows());

        List<MessageDo> messageDos = Lists.newArrayList();
        for (Row row : resp.getRows()) {
            messageDos.add(MessageDo.fromRow(row));
        }
        return  messageDos;

    }
}
