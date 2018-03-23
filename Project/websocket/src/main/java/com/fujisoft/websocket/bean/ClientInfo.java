package com.fujisoft.websocket.bean;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.Session;

/** 
* create by 張風武 
* 2017/08/21 14:10:39 
*/
public class ClientInfo {
    private String roomId;
    private String user;
    public Session session;
    public Session getSession() {
        return session;
    }
    public void setSession(Session session) {
        this.session = session;
    }
    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
}
