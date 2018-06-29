package com.fujisoft.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.fujisoft.test.R;

public class TransparentBackgroundImageActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent_background_image);
        imageView= (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TransparentBackgroundImageActivity.this, "这里是我的地盘！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
