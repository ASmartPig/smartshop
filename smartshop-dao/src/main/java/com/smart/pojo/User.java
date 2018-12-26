package com.smart.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String id;
    private String account;
    private String password;
    private String email;
    private String phone;
    private String headimg_url;
    private int status;
    private int sex;
    private Date date_create;
    private Date date_update;
    private Date date_delete;
    private String last_login_ip;
    private int is_delete;
    private String userName;

    public User(String id, String account, String password, String userName) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.userName = userName;
    }

    public User(){

    }
}
