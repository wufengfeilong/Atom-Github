package com.fujisoft.websocket.bean;
/** 
* create by 張風武 
* 2017/08/21 14:37:58 
*/
public class ToUserMsg {
    private String userId;
    private String msg;
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
