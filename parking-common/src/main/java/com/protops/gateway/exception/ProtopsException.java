package com.protops.gateway.exception;

/**
 * Created by zhouzhihao on 2014/10/21.
 */
public class ProtopsException extends Exception {

    private static final long serialVersionUID = 6034592482590653805L;

    public ProtopsException() {
        super();
    }

    public ProtopsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtopsException(String message) {
        super(message);
    }

    public ProtopsException(Throwable cause) {
        super(cause);
    }

}
