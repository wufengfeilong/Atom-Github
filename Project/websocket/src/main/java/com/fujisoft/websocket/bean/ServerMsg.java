package com.fujisoft.websocket.bean;
/** 
* create by 張風武 
* 2017/08/21 14:10:57 
*/
public class ServerMsg {
    private String rspMsg;
    
    public ServerMsg(String rspMsg){
        this.rspMsg = rspMsg;
    }
    
    public String getRspMsg() {
        return rspMsg;
    }

    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }
}
