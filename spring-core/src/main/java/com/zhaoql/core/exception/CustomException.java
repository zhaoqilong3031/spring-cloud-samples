package com.zhaoql.core.exception;

import java.util.ArrayList;
import java.util.List;

import com.zhaoql.core.exception.ErrorHolder.CodeTemp;

public class CustomException extends RuntimeException {

    private static final long serialVersionUID = -360277845666981697L;

    private String errorCode = CodeTemp.UNKNOW.getCode();

    private List<CustomException> relationExceptions;


    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void addExceptions(CustomException exception) {
        relationExceptions = relationExceptions == null ? new ArrayList<>() : relationExceptions;
        relationExceptions.add(exception);
    }

}
