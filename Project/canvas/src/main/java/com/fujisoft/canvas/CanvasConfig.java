package com.fujisoft.canvas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/** 
* create by 張風武 
* 2017/09/01 10:24:22 
*/
@Configuration
public class CanvasConfig {
    @Bean  
    public ServerEndpointExporter serverEndpointExporter (){  
        return new ServerEndpointExporter();  
    } 
}
