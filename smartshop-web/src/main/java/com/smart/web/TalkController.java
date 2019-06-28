package com.smart.web;

import com.google.common.collect.Lists;
import com.smart.configuration.ClientConfig;
import com.smart.mock.Result;
import com.smart.model.MessageDo;
import com.smart.tablestore.InsertRowOperation;
import com.smart.tablestore.OperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description
 * @Author liaoze
 * @Date 2019/6/23 下午12:05
 **/
@Api(description = "tableline测试")
@Controller
@Slf4j
@RequestMapping("/talk")
public class TalkController {

    @Autowired
    private OperationService operationService;


    @Autowired
    private ClientConfig clientConfig;

    @Autowired
    private InsertRowOperation insertRowOperation;

    @ApiOperation(value = "发送消息（POST)", notes = "用户注册 ： http method is post", httpMethod = "POST")
    @RequestMapping("/register")
    @ResponseBody
    public Result<String> sendMessage(@RequestParam(name = "sendWechatId") String sendWechatId, @RequestParam(name = "talkId") String talkId, @RequestParam(name = "isGroup") String isGroup, @RequestParam(name = "content") String content) {
        String timelineid = null;
        if ("no".equals(isGroup)) {
            timelineid = operationService.queryTimelineId(clientConfig.getClient(), sendWechatId, talkId);
        }

        if (Objects.isNull(timelineid)){
            timelineid = operationService.queryTimelineId(clientConfig.getClient(), talkId, sendWechatId);
        }

        if ("yes".equals(isGroup)) {
            timelineid = operationService.queryGroupTimelineId(clientConfig.getClient(), talkId);
        }

        MessageDo messageDo = new MessageDo();
        messageDo.setTimeline_id(Objects.isNull(timelineid) ? UUID.randomUUID().toString() : timelineid);
        messageDo.setSender_id(sendWechatId);
        messageDo.setReceiver_id(talkId);

        messageDo.setMessage_id(UUID.randomUUID().toString());
        messageDo.setMessage_type("message");
        messageDo.setMessage_content(content);
        messageDo.setIs_group_message(isGroup);

        messageDo.setSend_time(new Date());
        messageDo.setCreate_time(new Date());
        messageDo.setUpdate_time(new Date());
        insertRowOperation.putRow(clientConfig.getClient(), messageDo);
        return Result.success("success");
    }


    @ApiOperation(value = "查看历史记录（POST)", notes = "用户注册 ： http method is post", httpMethod = "POST")
    @RequestMapping("/viewHistory")
    @ResponseBody
    public List<MessageDo> ViewChatHistory(String currentWechatId, String talkId, String isGroup) {
        String timelineid = null;
        if ("no".equals(isGroup)) {
            timelineid = operationService.queryTimelineId(clientConfig.getClient(), currentWechatId, talkId);
        }

        if ("yes".equals(isGroup)) {
            timelineid = operationService.queryGroupTimelineId(clientConfig.getClient(), talkId);
        }
        List<MessageDo> messageDos = operationService.matchQuery(clientConfig.getClient(),timelineid);

        return messageDos;
    }

    public static void main(String[] args) {
        InsertRowOperation insertRowOperation = new InsertRowOperation();
        ClientConfig clientConfig = new ClientConfig();

        MessageDo messageDo = new MessageDo();
        messageDo.setTimeline_id(UUID.randomUUID().toString());
        messageDo.setSender_id("liaoze");
        messageDo.setReceiver_id("concrete");

        messageDo.setMessage_id(UUID.randomUUID().toString());
        messageDo.setMessage_type("message");
        messageDo.setMessage_content("测试两下");
        messageDo.setIs_group_message("no");

        messageDo.setSend_time(new Date());
        messageDo.setCreate_time(new Date());
        messageDo.setUpdate_time(new Date());
        insertRowOperation.putRow(clientConfig.getClient(), messageDo);


     /*   SearchRecordOperation searchRecordOperation = new SearchRecordOperation();
        MessageDo messageDo = new MessageDo();
        messageDo.setSender_id("liaoze");
        messageDo.setReceiver_id("water");
        searchRecordOperation.batchGetRow(clientConfig.getClient());*/

    /*    IndexMeta indexMeta = new IndexMeta("timeline_id_search"); // 要创建的索引表名称。
        indexMeta.addPrimaryKeyColumn("sender_id"); // 为索引表添加主键列。
        indexMeta.addPrimaryKeyColumn("receiver_id"); // 为索引表添加主键列。
        indexMeta.addDefinedColumn("timelineid"); // 为索引表添加属性列。
        CreateIndexRequest request = new CreateIndexRequest("message", indexMeta, false);
        clientConfig.getClient().createIndex(request);*/

    }


    private String getTimelineId(String currentWechatId, String talkId) {
        String msgId = operationService.queryTimelineId(clientConfig.getClient(), currentWechatId, talkId);
        if (Objects.isNull(msgId)) {
            msgId = operationService.queryTimelineId(clientConfig.getClient(), talkId, currentWechatId);
        }
        return msgId;
    }
}
