package com.fujisoft.test;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.github.barteksc.pdfviewer.PDFView;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class pdfViewActivity extends AppCompatActivity {
    PDFView pdfView;
    CanvasView canvasView;
    private final String URL = "ws://172.29.140.35:5001/canvas";
    MyWebSocket socket;
    Uri pdfUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        findViews();
        initViews();
        setListeners();
    }

    private void setListeners() {
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
        pdfUri = getIntent().getParcelableExtra("pdfUri");
        Toast.makeText(this, "pdfUri:"+pdfUri, Toast.LENGTH_SHORT).show();
        pdfView.fromUri(pdfUri)
                .defaultPage(1)
                .swipeHorizontal(false)
                .enableSwipe(true)
                .load();

        try {
            socket = new MyWebSocket(new URI(URL));
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void findViews() {
        canvasView = (CanvasView) findViewById(R.id.canvasPdfView);

        pdfView = (PDFView) findViewById(R.id.pdfView);
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
                    Toast.makeText(pdfViewActivity.this, "连接上，可以开始画了！", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onMessage(String message) {
            final String[] arr = message.split(",");
            if (arr[0].equals("move")) {
                canvasView.rPathMoveTo(Float.parseFloat(arr[1]),Float.parseFloat(arr[2]));
            } else if (arr[0].equals("line")) {
                canvasView.rPathLineTo(Float.parseFloat(arr[1]),Float.parseFloat(arr[2]));
            } else if (arr[0].equals("page")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pdfView.jumpTo(Integer.parseInt(arr[1]));
                    }
                });
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(pdfViewActivity.this, "已关闭！", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onError(final Exception ex) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(pdfViewActivity.this, "发生错误！"+ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void prePage(View v){
        int toPage = pdfView.getCurrentPage()-1;
        pdfView.jumpTo(toPage);
        socket.send("page,"+toPage);

    }
    public void nextPage(View v){
        int toPage = pdfView.getCurrentPage()+1;
        pdfView.jumpTo(toPage);
        socket.send("page,"+toPage);
    }
}
