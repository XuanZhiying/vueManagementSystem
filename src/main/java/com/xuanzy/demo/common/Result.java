package com.xuanzy.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: 轩志颍
 * Create: 2022-04-20 17:37
 * 返回结果的包装类
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private String code;

    private String msg;

    private Object data;

    public static Result success(){
        return new Result(Constants.CODE_200,"",null);
    }

    public static Result success(Object data){
        return new Result(Constants.CODE_200,"",data);
    }

    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }

    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }




}
