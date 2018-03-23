package com.fujisoft.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.fujisoft.websocket.bean.ClientInfo;
import com.fujisoft.websocket.bean.ServerMsg;

/** 
* create by 張風武 
* 2017/08/22 14:17:00 
*/
@Controller
public class WebSocketController {
//    @MessageMapping("/welcome")
//    @SendTo("/topic/getRsp")
//    public ServerMsg sayToClient(ClientMsg clientMsg){
//        
//        return new ServerMsg("Hello,"+clientMsg.getReqMsg());
//    }
    
}
