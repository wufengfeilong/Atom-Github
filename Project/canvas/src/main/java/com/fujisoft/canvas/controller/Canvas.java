package com.fujisoft.canvas.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

/** 
* create by 張風武 
* 2017/09/01 10:26:01 
*/
@ServerEndpoint("/canvas")
@Component
public class Canvas {
    private static Set<Session> sessions = new HashSet<>();
    private Session session;
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        sessions.add(session);
        System.out.println(session.getRequestURI()+"进来了！");
    }
    
    @OnClose
    public void onClose(){
        sessions.remove(session);
        System.out.println(session.getRequestURI()+"退出了！");
    }
    
    @OnMessage
    public void onMessage(String message, Session session){
        sendToAll(message);
        System.out.println(session.getRequestURI()+"发来信息："+message);
    }
    // 发送给除了自己的所有的聊天者
    private void sendToAll(String text) {

        for (Session session : sessions) {
            if (this.session != session) {
//                synchronized (session) {
                    try {
                        session.getBasicRemote().sendText(text);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
//                }
            }
        }
    }
}
