package com.fujisoft.websocket.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.fujisoft.websocket.bean.ClientInfo;

/** 
* create by 張風武 
* 2017/08/21 17:43:12 
*/
@ServerEndpoint("/websocket/{roomId}/{user}")  
@Component 
public class MyWebSocket {
    private static int onlineCount = 0;  
    private static List<ClientInfo> lists = new ArrayList<>();
    private ClientInfo clientInfo = new ClientInfo();
    private String nickname;
    private String room;
    @OnOpen  
    public void onOpen (Session session,
            @PathParam(value="roomId") String roomId,
            @PathParam(value="user") String user){
        clientInfo.setSession(session);
        clientInfo.setRoomId(roomId);
        clientInfo.setUser(user);
        lists.add(clientInfo);
        nickname = user;
        room = roomId;
        // 群发消息  
//        sendToAll(user+"进来了"+roomId+"房间，大家请欢迎！");
        // 同房间发消息
        sendToSameRoom(user+"进来了"+roomId+"房间，大家请欢迎！");
        addOnlineCount();  
        System.out.println("有新链接加入!当前在线人数为" + getOnlineCount());  
    }  
  
    @OnClose  
    public void onClose (){  
        lists.remove(clientInfo);
        subOnlineCount();
        // 群发消息  
//        sendToAll(nickname+"退出了，请大家鄙视！");
        // 同房间发消息
        sendToSameRoom(nickname+"退出了，请大家鄙视！");
        System.out.println("有一链接关闭!当前在线人数为" + getOnlineCount());  
    }  
  
    @OnMessage  
    public void onMessage (String message, Session session) throws IOException {  
        System.out.println(nickname+":" + message);  
        // 群发消息  
//        sendToAll(nickname+":" + message);
        // 同房间发消息
        sendToSameRoom(nickname+":" + message);
    }  
  
    public static synchronized  int getOnlineCount (){  
        return MyWebSocket.onlineCount;  
    }  
  
    public static synchronized void addOnlineCount (){  
        MyWebSocket.onlineCount++;  
    }  
  
    public static synchronized void subOnlineCount (){  
        MyWebSocket.onlineCount--;  
    }
    // 发送给所有的聊天者
    private void sendToAll(String text) {

        for (ClientInfo list : lists) {
            synchronized (list) {
                list.getSession().getAsyncRemote().sendText(text);
            }
        }
    }
    
    // 发送给同房间的聊天者
    private void sendToSameRoom(String text) {

        for (ClientInfo list : lists) {
            System.out.println(room);
            System.out.println(list.getRoomId());
            if (room.equals(list.getRoomId())) {
                    list.getSession().getAsyncRemote().sendText(text);
                
            }
        }
    }
  
}
