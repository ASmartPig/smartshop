package com.smart.enums;

public enum  ConsumeResult {
    /**
     * 消费成功
     */
    CommitMessage("CommitMessage", "消费成功，继续消费下一条消息"),

    /**
     *消费失败
     */
    ReconsumeLater("ReconsumeLater", "消费失败，告知服务器稍后再投递这条消息，继续消费其他消息");

    private String code;
    private String desc;

     ConsumeResult(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}