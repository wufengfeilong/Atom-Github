package com.fujisoft.test;

import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class CanvasActivity extends AppCompatActivity {
    private CanvasView canvasView;
    Toolbar toolbar;
    FloatingActionButton fab;
    MyWebSocket socket;
    private final String URL = "ws://172.29.140.35:5001/canvas";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        findViews();
        initViews();
        setListeners();
    }

    private void setListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvasView.clear();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        canvasView.setTouchI(new CanvasView.TouchI() {
            @Override
            public void rMoveTo(float x, float y) {
                socket.send("move,"+x+","+y);
            }

            @Override
            public void rLineTo(float x, float y) {
                socket.send("line,"+x+","+y);
            }
        });
    }

    private void initViews() {
        setSupportActionBar(toolbar);

        try {
            socket = new MyWebSocket(new URI(URL));
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        canvasView = (CanvasView) findViewById(R.id.canvasView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.close();
    }

    public class MyWebSocket extends WebSocketClient {


        public MyWebSocket(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CanvasActivity.this, "连接上，可以开始画了！", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onMessage(String message) {
            String[] arr = message.split(",");
            if (arr[0].equals("move")) {
                canvasView.rPathMoveTo(Float.parseFloat(arr[1]),Float.parseFloat(arr[2]));
            } else if (arr[0].equals("line")) {
                canvasView.rPathLineTo(Float.parseFloat(arr[1]),Float.parseFloat(arr[2]));
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CanvasActivity.this, "已关闭！", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onError(final Exception ex) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CanvasActivity.this, "发生错误！"+ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
