package com.smart.common.exceptions;

public class ExceptionBase extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int httpCode = 500;
    private String errCode;
    private boolean fault;
   // private String traceId;
    private boolean fromExceptionsMethod;
   // private String appName;

    public ExceptionBase() {
        this.errCode = "" + ErrorCodes.UNKNOW_EXCEPTION.getHttpCode();
        this.setupTraceInfo();
    }

    public ExceptionBase(int httpCode, String errCode) {
        this.errCode = "" + ErrorCodes.UNKNOW_EXCEPTION.getHttpCode();
        this.httpCode = httpCode;
        this.errCode = errCode;
        this.setupTraceInfo();
    }

    public ExceptionBase(String errCode, String errorMsg) {
        super(errorMsg);
        this.errCode = "" + ErrorCodes.UNKNOW_EXCEPTION.getHttpCode();
        this.errCode = errCode;
        this.setupTraceInfo();
    }

    public ExceptionBase(int httpCode, String errCode, String errorMsg, Throwable e) {
        super(errorMsg, e);
        this.errCode = "" + ErrorCodes.UNKNOW_EXCEPTION.getHttpCode();
        this.httpCode = httpCode;
        this.errCode = errCode;
        this.setupTraceInfo();
    }

    public ExceptionBase(int httpCode, String errCode, String errorMsg) {
        super(errorMsg);
        this.errCode = "" + ErrorCodes.UNKNOW_EXCEPTION.getHttpCode();
        this.httpCode = httpCode;
        this.errCode = errCode;
        this.setupTraceInfo();
    }

    public ExceptionBase(String errorMsg) {
        super(errorMsg);
        this.errCode = "" + ErrorCodes.UNKNOW_EXCEPTION.getHttpCode();
        this.setupTraceInfo();
    }

    public ExceptionBase(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errCode = "" + ErrorCodes.UNKNOW_EXCEPTION.getHttpCode();
        this.setupTraceInfo();
    }

    public ExceptionBase(Throwable cause) {
        super(cause);
        this.errCode = "" + ErrorCodes.UNKNOW_EXCEPTION.getHttpCode();
        this.setupTraceInfo();
    }

    protected void setupTraceInfo() {
//        this.traceId = SoucheTraceUtil.getTraceId();
//        this.appName = SoucheTraceUtil.getAppName();
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public boolean isFault() {
        return this.fault;
    }

    public void setFault(boolean isFault) {
        this.fault = isFault;
    }

//    public String getTraceId() {
//        return this.traceId;
//    }

//    public String getAppName() {
//        return this.appName;
//    }

    public boolean isFromExceptionsMethod() {
        return this.fromExceptionsMethod;
    }

    public void setFromExceptionsMethod(boolean fromExceptionsMethod) {
        this.fromExceptionsMethod = fromExceptionsMethod;
    }
}
