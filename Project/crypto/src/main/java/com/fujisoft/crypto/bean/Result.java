package com.fujisoft.crypto.bean;
/** 
* create by 張風武 
* 2018/02/23 11:15:42 
*/
public class Result {
    int code;
    String msg;
    Object data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
