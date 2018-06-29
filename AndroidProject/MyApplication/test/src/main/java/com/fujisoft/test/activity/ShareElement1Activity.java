package com.fujisoft.test.activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.fujisoft.test.R;

import static com.fujisoft.test.R.id.imageView3;

public class ShareElement1Activity extends AppCompatActivity {
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_element1);
        iv = (ImageView) findViewById(R.id.imageView3);
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void toShareElement2(View v){
        Intent intent = new Intent(this, ShareElement2Activity.class);
        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, iv, "share");
        startActivity(intent,optionsCompat.toBundle());
    }
}
