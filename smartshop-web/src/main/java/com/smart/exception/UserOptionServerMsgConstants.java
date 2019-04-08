package com.smart.exception;

import com.smart.common.exceptions.ServerMsg;
import com.smart.common.exceptions.ServerMsgConstants;

public class UserOptionServerMsgConstants extends ServerMsgConstants {
    public static final ServerMsg USER_PARAM_IS_NULL = new ServerMsg("USER_PARAM_IS_NULL", "登陆账号参数为空");

    public static final ServerMsg USER_ACCOUNT_IS_EXIST = new ServerMsg("USER_ACCOUNT_IS_EXIST", "账号已存在");

    public static final ServerMsg USER_ACCOUNT_IS_NOT_EXIST = new ServerMsg("USER_ACCOUNT_IS_NOT_EXIST", "账号不存在");

    public static final ServerMsg PASSWORD_IS_INCORRECT = new ServerMsg("USER_ACCOUNT_IS_NOT_EXIST", "密码不正确");

    public static final ServerMsg A = new ServerMsg("A", "A");

    public static final ServerMsg B = new ServerMsg("B", "B");

}
