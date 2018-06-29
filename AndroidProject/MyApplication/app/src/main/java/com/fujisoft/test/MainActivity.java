package com.fujisoft.test;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void toDrawing(View v){
        startActivity(new Intent(this,CanvasActivity.class));
    }
    public void openPDF(View v){
        // 動画呼び出し
        Intent intent = new Intent();
        // タイプ：動画
        intent.setType("*/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            if (data.getData()!=null) {
                Uri uri = data.getData();
                Intent intent = new Intent(this,pdfViewActivity.class);
                intent.putExtra("pdfUri",uri);
                startActivity(intent);
            }
        }
    }
}
