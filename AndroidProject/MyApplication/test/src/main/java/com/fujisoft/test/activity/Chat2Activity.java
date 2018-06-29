package com.fujisoft.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.fujisoft.test.R;
import com.fujisoft.test.util.WebSocketUtil;

public class Chat2Activity extends AppCompatActivity{
    MyApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat2);
        app = (MyApplication) getApplication();
        app.socket.setSocketListener(new WebSocketUtil.SocketListener() {
            @Override
            public void onMsg(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Chat2Activity.this, "chat2来处理："+msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
