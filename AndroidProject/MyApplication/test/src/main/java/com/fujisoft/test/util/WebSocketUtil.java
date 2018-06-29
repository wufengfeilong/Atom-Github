package com.fujisoft.test.util;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketUtil extends WebSocketClient {
    public interface SocketListener{
        void onMsg(String msg);
    }
    public SocketListener socketListener;

    public void setSocketListener(SocketListener socketListener) {
        this.socketListener = socketListener;
    }

    public WebSocketUtil(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
    }

    @Override
    public void onMessage(String message) {
        socketListener.onMsg(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
    }

    @Override
    public void onError(Exception ex) {
    }
}
