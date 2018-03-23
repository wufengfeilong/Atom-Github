package com.example.demo.bean;
/** 
* create by 張風武 
* 2017/09/04 10:55:46 
*/


public class Result {
    private int code;
    private String msg;
    public Result(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
}
