package com.xuecheng.base.exception;

public class XueChengPlusException extends RuntimeException{

    private String errMessage;

    public XueChengPlusException() {
        super();
    }

    public XueChengPlusException(String message) {
        super(message);
        this.errMessage = message;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public static void cast(String errMessage){
        throw new XueChengPlusException(errMessage);
    }

}
