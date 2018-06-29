package com.fcn.park.base.widget;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fcn.park.R;


/**
 * Created by lvzp on 2017/4/20.
 * 类描述：
 */

public class TextTitlePopWindow extends TitleBasePopWindow {

    protected TextView mTitleView;

    protected OnClickSaveCallback mClickSaveCallback;

    private String mTitleName;

    /**
     * 初始化一个PopupWindow
     *
     * @param context 上下文对象
     * @param resId   自定义的布局文件
     */
    public TextTitlePopWindow(Activity context, int resId) {
        super(context, resId);
        mTitleView = new TextView(context);
        mTitleView.setTextSize(18);
        mTitleView.setTextColor(ContextCompat.getColor(context, R.color.colorTitleColor));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        mTitleView.setLayoutParams(lp);
        initTitleLayout();
        setRightText("保存");
    }

    @Override
    public View getAddContentView() {
        return mTitleView;
    }

    public void setTitleText(String titleText) {
        mTitleName = titleText;
        mTitleView.setText(titleText);
    }

    public void setOnClickSaveCallback(OnClickSaveCallback c) {
        mClickSaveCallback = c;
    }

    public String getTitleName() {
        return mTitleName;
    }

    public interface OnClickSaveCallback {
        /**
         * 输入框中的数据点击保存时的回调
         *
         * @param content 输入的具体内容
         */
        void onSaveCallback(String content);
    }
}
