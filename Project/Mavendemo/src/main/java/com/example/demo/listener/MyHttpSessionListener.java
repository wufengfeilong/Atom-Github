package com.example.demo.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/** 
* create by 張風武 
* 2017/09/06 14:56:18 
*/
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // TODO Auto-generated method stub
        System.out.println("Session 被创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // TODO Auto-generated method stub
        System.out.println("ServletContex初始化");
    }

}
