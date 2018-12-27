package com.smart.common.exceptions;

public class ServerMsg {
    private String code;
    private String desc;

    public ServerMsg(String code, String desc) {
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
