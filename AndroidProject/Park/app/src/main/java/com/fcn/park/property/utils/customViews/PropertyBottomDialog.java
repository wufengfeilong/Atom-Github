package com.fcn.park.property.utils.customViews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.fcn.park.R;

/**
 * Created by 860115032 on 2018/04/17.
 */

public class PropertyBottomDialog extends Dialog {

    //控制点击dialog外部是否dismiss
    private boolean iscancelable;
    //控制返回键是否dismiss
    private boolean isBackCancelable;
    private View view;
    private Context context;

    public PropertyBottomDialog(Context context, View view, boolean isCancelable,boolean isBackCancelable) {
        super(context, R.style.CustomDialog);
        this.context = context;
        this.view = view;
        this.iscancelable = isCancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);//这行一定要写在前面
        setCancelable(true);//点击外部不可dismiss
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

    }
}
