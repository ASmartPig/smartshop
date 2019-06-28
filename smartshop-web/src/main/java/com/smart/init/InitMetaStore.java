package com.smart.init;

import com.alicloud.openservices.tablestore.model.search.FieldSchema;
import com.alicloud.openservices.tablestore.model.search.FieldType;
import com.alicloud.openservices.tablestore.model.search.IndexSchema;
import com.alicloud.openservices.tablestore.timeline2.TimelineMetaStore;
import com.alicloud.openservices.tablestore.timeline2.TimelineStore;
import com.alicloud.openservices.tablestore.timeline2.TimelineStoreFactory;
import com.alicloud.openservices.tablestore.timeline2.core.TimelineStoreFactoryImpl;
import com.alicloud.openservices.tablestore.timeline2.model.*;
import com.smart.configuration.ClientConfig;

import java.util.Arrays;

/**
 * @Description
 * @Author liaoze
 * @Date 2019/6/25 下午2:58
 **/
public class InitMetaStore {


    public static void main(String[] args) {
        InitMetaStore initMetaStore = new InitMetaStore();
        initMetaStore.init();
    }
    public void init(){
        //1、初始化factory
        ClientConfig clientConfig = new ClientConfig();
        TimelineStoreFactory factory = new TimelineStoreFactoryImpl(clientConfig.getClient());
        TimelineIdentifierSchema idSchema = new TimelineIdentifierSchema.Builder()
                .addStringField("timeline_id").build();

        //2、初始化MetaStore
        IndexSchema metaIndex = new IndexSchema();
        //配置索引字段、类型
        metaIndex.addFieldSchema(new FieldSchema("group_name", FieldType.TEXT).setIndex(true).setAnalyzer(FieldSchema.Analyzer.MaxWord));
        metaIndex.addFieldSchema( new FieldSchema("create_time", FieldType.LONG).setIndex(true));

        TimelineMetaSchema metaSchema = new TimelineMetaSchema("groupMeta", idSchema)
                .withIndex("metaIndex", metaIndex); //设置索引
        TimelineMetaStore timelineMetaStore = factory.createMetaStore(metaSchema);

        //3、初始化TimelineStore
         idSchema = new TimelineIdentifierSchema.Builder()
                .addStringField("timeline_id").build();

        IndexSchema timelineIndex = new IndexSchema();
        timelineIndex.setFieldSchemas(Arrays.asList(//配置索引的字段、类型
                new FieldSchema("text", FieldType.TEXT).setIndex(true).setAnalyzer(FieldSchema.Analyzer.MaxWord),
                new FieldSchema("receivers", FieldType.KEYWORD).setIndex(true).setIsArray(true)
        ));

        TimelineSchema timelineSchema = new TimelineSchema("timeline", idSchema)
                .autoGenerateSeqId() //SequenceId 设置为自增列方式
                .setCallbackExecuteThreads(5) //设置Writer初始线程数为5
                .withIndex("metaIndex", timelineIndex); //设置索引

        TimelineStore timelineStore = factory.createTimelineStore(timelineSchema);



    }


    public  void testMeta(){
        //1、初始化factory
        ClientConfig clientConfig = new ClientConfig();
        TimelineStoreFactory factory = new TimelineStoreFactoryImpl(clientConfig.getClient());
        TimelineIdentifierSchema idSchema = new TimelineIdentifierSchema.Builder()
                .addStringField("timeline_id").build();


        //2、初始化MetaStore
        IndexSchema metaIndex = new IndexSchema();
        //配置索引字段、类型
        metaIndex.addFieldSchema(new FieldSchema("group_name", FieldType.TEXT).setIndex(true).setAnalyzer(FieldSchema.Analyzer.MaxWord));
        metaIndex.addFieldSchema( new FieldSchema("create_time", FieldType.LONG).setIndex(true));

        TimelineMetaSchema metaSchema = new TimelineMetaSchema("groupMeta", idSchema)
                .withIndex("metaIndex", metaIndex); //设置索引
        TimelineMetaStore timelineMetaStore = factory.createMetaStore(metaSchema);


        TimelineIdentifier identifier = new TimelineIdentifier.Builder()
                .addField("timeline_id", "group")
                .build();
        TimelineMeta meta = new TimelineMeta(identifier)
                .setField("filedName", "fieldValue");
        /**
         * 创建Meta表（如果设置索引则会创建索引）
         * */
        timelineMetaStore.prepareTables();

/**
 * 插入Meta数据
 * */
        timelineMetaStore.insert(meta);





    }
}
