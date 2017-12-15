package com.fujisoft.card;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

public class IToast {
    Context context;

    public IToast(Context context) {
        this.context = context;
    }

    public void imgToast(int resId) {
        Toast toast = new Toast(context);
        // 设置土司显示的时间长短
        toast.setDuration(Toast.LENGTH_SHORT);
        // 创建ImageView
        ImageView img = new ImageView(context);
        // 设置图片的资源路径
        img.setImageResource(resId);
        // 设置土司的视图图片
        toast.setView(img);
        // 设置土司显示在屏幕的位置,默认居中
//        toast.setGravity(Gravity.CENTER,0,0);
        // 显示土司
        toast.show();
    }
}
