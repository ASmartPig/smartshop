package com.smart.common.exceptions;

import java.text.MessageFormat;

public class ServerException extends ExceptionBase{
    private static final long serialVersionUID = 1L;
    private static final int HTTP_CODE = 500;

    public ServerException(ServerMsg serverMsg) {
        super(500, serverMsg.getCode(), serverMsg.getDesc());
    }

    public ServerException(ServerMsg serverMsg, Object... arguments) {
        super(500, serverMsg.getCode(), MessageFormat.format(serverMsg.getDesc(), arguments));
    }

    public ServerException(ServerMsg serverMsg, Throwable throwable) {
        super(500, serverMsg.getCode(), serverMsg.getDesc(), throwable);
    }

    public ServerException(ServerMsg serverMsg, Throwable throwable, Object... arguments) {
        super(500, serverMsg.getCode(), MessageFormat.format(serverMsg.getDesc(), arguments), throwable);
    }
}
