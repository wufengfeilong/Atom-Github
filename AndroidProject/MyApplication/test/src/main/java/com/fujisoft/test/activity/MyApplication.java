package com.fujisoft.test.activity;

import android.app.Application;
import com.fujisoft.test.util.WebSocketUtil;

import java.net.URI;
import java.net.URISyntaxException;

public class MyApplication extends Application {
    private final String URL = "ws://172.29.140.35:5000/websocket/101/zfw";
//    private final String URL = "ws://172.29.140.35:5000/websocket/101/lyx";
    public static WebSocketUtil socket;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            socket = new WebSocketUtil(new URI(URL));
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
