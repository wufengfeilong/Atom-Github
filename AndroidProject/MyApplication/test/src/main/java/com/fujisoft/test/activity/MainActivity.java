package com.fujisoft.test.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.fujisoft.test.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toWordShow(View v){
        Intent intent = new Intent(this,WordShowActivity.class);
        startActivity(intent);
    }

    public void toAnswer(View v){
        Intent intent = new Intent(this,TabbedActivity.class);
        startActivity(intent);
    }

    public void toRxJava(View v){
        Intent intent = new Intent(this,RxJavaActivity.class);
        startActivity(intent);
    }

    public void toMVP(View v){
        Intent intent = new Intent(this,MVPModelActivity.class);
        startActivity(intent);
    }

    public void toStackView(View v){
        Intent intent = new Intent(this,StackViewActivity.class);
        startActivity(intent);
    }

    public void toTransBGImg(View v){
        Intent intent = new Intent(this,TransparentBackgroundImageActivity.class);
        startActivity(intent);
    }

    public void toShareElement(View v){
        Intent intent = new Intent(this,ShareElement1Activity.class);
        startActivity(intent);
    }

    public void toRemoveImage(View v){
        Intent intent = new Intent(this,RomoveImageActivity.class);
        startActivity(intent);
    }
}
