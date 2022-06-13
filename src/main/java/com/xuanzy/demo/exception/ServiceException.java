package com.xuanzy.demo.exception;

/**
 * Author: 轩志颍
 * Create: 2022-04-21 17:54
 * 自定义异常
 **/
public class ServiceException extends RuntimeException{
    private final String code;

    public ServiceException(String code,String msg){
        super(msg);
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
