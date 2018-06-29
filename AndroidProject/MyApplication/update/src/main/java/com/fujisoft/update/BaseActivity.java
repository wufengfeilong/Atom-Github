package com.fujisoft.update;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        findViews();
        initViews();
        addListeners();
    }
    public abstract void setView();
    public abstract void findViews();
    public abstract void initViews();
    public abstract void addListeners();
}
