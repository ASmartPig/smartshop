package com.smart.model;

import com.alicloud.openservices.tablestore.model.Column;
import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.Row;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author liaoze
 * @Date 2019/6/23 下午4:13
 **/
public class MessageDo {

    private String timeline_id;

    private String sender_id;
    private String receiver_id;
    private Integer sequence_id;

    private String message_id;
    private String message_type;
    private String message_content;
    private String is_group_message;

    private Date send_time;
    private Date create_time;
    private Date update_time;

    public String getTimeline_id() {
        return timeline_id;
    }

    public void setTimeline_id(String timeline_id) {
        this.timeline_id = timeline_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public String getIs_group_message() {
        return is_group_message;
    }

    public void setIs_group_message(String is_group_message) {
        this.is_group_message = is_group_message;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getSequence_id() {
        return sequence_id;
    }

    public void setSequence_id(Integer sequence_id) {
        this.sequence_id = sequence_id;
    }

    public MessageDo() {
    }

    public MessageDo(String timeline_id, String sender_id, String receiver_id, Integer sequence_id, String message_id, String message_type, String message_content, String is_group_message, Date send_time, Date create_time, Date update_time) {
        this.timeline_id = timeline_id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.sequence_id = sequence_id;
        this.message_id = message_id;
        this.message_type = message_type;
        this.message_content = message_content;
        this.is_group_message = is_group_message;
        this.send_time = send_time;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    private static <T> T getLatestColumnValue(Row row, String name, Class<T> valueType) {
        Column column = row.getLatestColumn(name);

        if (String.class == valueType) {
            return (T) column.getValue().asString();
        } else if (Long.class == valueType || long.class == valueType) {
            return (T) Long.valueOf(column.getValue().asLong());
        } else if (boolean.class == valueType || Boolean.class == valueType) {
            return (T) Boolean.valueOf(column.getValue().asBoolean());
        } else if (double.class == valueType || Double.class == valueType) {
            return (T) Double.valueOf(column.getValue().asDouble());
        }
        throw new UnsupportedOperationException("Unknown type " + valueType);
    }


    public static MessageDo fromRow(Row row) {
        MessageDo messageDo = new MessageDo();
        messageDo.setTimeline_id(row.getPrimaryKey().getPrimaryKeyColumn("timelineid").getValue().asString());
        messageDo.setSender_id(row.getPrimaryKey().getPrimaryKeyColumn("sender_id").getValue().asString());
        messageDo.setReceiver_id(row.getPrimaryKey().getPrimaryKeyColumn("receiver_id").getValue().asString());
        messageDo.setSequence_id((int) row.getPrimaryKey().getPrimaryKeyColumn("sequence_id").getValue().asLong());
    //    messageDo.setSender_id(getLatestColumnValue(row, "sender_id", String.class));
     //   messageDo.setReceiver_id(getLatestColumnValue(row, "receiver_id", String.class));
      //  messageDo.setSequence_id(getLatestColumnValue(row, "sequence_id", Integer.class));
        messageDo.setMessage_id(getLatestColumnValue(row, "message_id", String.class));
        messageDo.setMessage_type(getLatestColumnValue(row, "message_type", String.class));
        messageDo.setMessage_content(getLatestColumnValue(row, "message_content", String.class));
        messageDo.setIs_group_message(getLatestColumnValue(row, "is_group_message", String.class));
//        messageDo.setSend_time(getLatestColumnValue(row, "send_time", Date.class));
//        messageDo.setCreate_time(getLatestColumnValue(row, "create_time", Date.class));
//        messageDo.setUpdate_time(getLatestColumnValue(row, "update_time", Date.class));
        return  messageDo;
    }
}
