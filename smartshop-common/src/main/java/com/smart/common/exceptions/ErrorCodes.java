package com.smart.common.exceptions;

public class ErrorCodes {
    public static final ErrorMessage CROSS_SHOP = ErrorMessage.errorMessage(402, "CROSS_SHOP", "跨店访问异常");
    public static final ErrorMessage UNKNOW_EXCEPTION = ErrorMessage.errorMessage(500, "UNKNOW_EXCEPTION", "未知异常");
    public static final ErrorMessage ITEM_NOT_EXISTS = ErrorMessage.errorMessage(404, "ITEM_NOT_EXISTS");
    public static final ErrorMessage LOGIN_BAD_PASSWORD = ErrorMessage.errorMessage(402, "LOGIN_BAD_PASSWORD");
    public static final ErrorMessage LOGIN_USER_NOT_EXIST = ErrorMessage.errorMessage(402, "LOGIN_USER_NOT_EXIST", "登录时用户不存在");
    public static final ErrorMessage NO_PERMISSION = ErrorMessage.errorMessage(402, "NO_PERMISSION", "未授权");
    public static final ErrorMessage NOT_LOGIN = ErrorMessage.errorMessage(401, "NOT_LOGIN", "未登录");
    public static final ErrorMessage USER_FORBIDDEN = ErrorMessage.errorMessage(403, "USER_FORBIDDEN", "用户禁止访问");
    public static final ErrorMessage TIME_OUT = ErrorMessage.errorMessage(504, "TIMEOUT", "访问超时");
    public static final ErrorMessage PARAM_CONVERT_ERR = ErrorMessage.errorMessage(417, "PARAM_CONVERT_ERR", "参数类型转换异常");
    public static final ErrorMessage PARAM_ERROR = ErrorMessage.errorMessage(417, "PARAM_ERROR", "参数处理异常");
    public static final ErrorMessage PARAM_MISS = ErrorMessage.errorMessage(417, "PARAM_MISS", "参数缺失");
    public static final ErrorMessage PARAM_VALIDATION_ERR = ErrorMessage.errorMessage(417, "PARAM_VALIDATION_ERR", "参数校验出错");
    public static final ErrorMessage PATH_NOT_EXISTS = ErrorMessage.errorMessage(404, "PATH_NOT_EXISTS", "路径不存在");
    public static final ErrorMessage DUBBO_ERROR = ErrorMessage.errorMessage(503, "DUBBO_ERROR", "内部服务异常");

    public ErrorCodes() {
    }

}
