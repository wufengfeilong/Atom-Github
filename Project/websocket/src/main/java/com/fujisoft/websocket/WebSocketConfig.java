package com.fujisoft.websocket;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/** 
* create by 張風武 
* 2017/08/21 13:23:57 
*/
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        //允许使用socketJs方式访问，访问点为chat，允许跨域
//        registry.addEndpoint("/hello").setAllowedOrigins("*").withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        //广播式应配置一个/topic 消息代理 点对点式应配置一个/queue消息代理
//        registry.enableSimpleBroker("/topic","/queue");
//        //全局使用的订阅前缀（客户端订阅路径上会体现出来）
////        registry.setApplicationDestinationPrefixes("/app/");
//        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
//        //registry.setUserDestinationPrefix("/user/");
//    }
//}
@Configuration  
public class WebSocketConfig {  
    @Bean  
    public ServerEndpointExporter serverEndpointExporter (){  
        return new ServerEndpointExporter();  
    }  
}  
