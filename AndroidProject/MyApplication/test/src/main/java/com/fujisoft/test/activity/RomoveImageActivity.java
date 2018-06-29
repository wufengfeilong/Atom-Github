package com.fujisoft.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.fujisoft.test.R;

public class RomoveImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_romove_image);
        Toast.makeText(this, "图片放大后再移动。", Toast.LENGTH_SHORT).show();
    }
}
