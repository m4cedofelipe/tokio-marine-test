package com.example.api.exception;

import org.springframework.http.HttpStatus;

public class CustomGenericException extends RuntimeException {

    private static final long serialVersionUID = 2457692790458351108L;

    private String errMsg;
    private HttpStatus statusCode;
    private Exception ex;

    public CustomGenericException(String errMsg, HttpStatus statusCode, Exception ex) {
        super(errMsg);
        this.statusCode = statusCode;
        this.ex = ex;
    }

    public CustomGenericException(String errMsg, HttpStatus statusCode) {
        this(errMsg, statusCode, null);
    }

    public CustomGenericException(String errMsg) {
        this(errMsg, HttpStatus.INTERNAL_SERVER_ERROR, null);
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getErrMsg() {
        if (errMsg == null) {
            errMsg = ex.getCause().toString();
        }

        return errMsg;
    }

    public String getEx() {
        if (ex != null) {
            return ex.getClass().getSimpleName();
        } else {
            return "CustomGenericException";
        }
    }
}
