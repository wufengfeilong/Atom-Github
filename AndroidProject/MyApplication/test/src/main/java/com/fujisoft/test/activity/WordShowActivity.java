package com.fujisoft.test.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.fujisoft.test.R;
import com.fujisoft.test.util.WordShowUtil;

public class WordShowActivity extends AppCompatActivity {
    String showWords = "君生我未生，我生君已老。<br>" +
                        "君恨我生迟，我恨君生早。<br>"+
                        "君生我未生，我生君已老。<br> " +
                        "恨不生同时，日日与君好。<br>"+
                        "我生君未生，君生我已老。<br> " +
                        "我离君天涯，君隔我海角。<br>"+
                        "我生君未生，君生我已老。<br> " +
                        "化蝶去寻花，夜夜栖芳草。<br><br><br>"+
            "<b>总有一天，你会找到一个人，让你心甘情愿地把她写在你代码中的注释里，但在心里，她永远都无法被注释。</b>" ;
    TextView wordShow;
    WordShowUtil wordShowUtil;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_show);

        wordShow = (TextView) findViewById(R.id.word_show);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        wordShowUtil = new WordShowUtil(wordShow, showWords, 100);//调用构造方法，直接开启

    }
}
