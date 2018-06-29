package com.fcn.park.base.widget;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import com.fcn.park.R;


/**
 * Created by lvzp on 2017/3/17.
 * 类描述：如果标题栏中是文字的话，建议他的子类{@link TextTitlePopWindow}文字标题
 */

public abstract class TitleBasePopWindow extends AddPopWindow {

    protected CheckBox mRightView;
    protected LinearLayout contentView;
    protected View leftView;

    /**
     * 初始化一个PopupWindow
     *
     * @param context 上下文对象
     * @param resId   自定义的布局文件
     */
    public TitleBasePopWindow(Activity context, int resId) {
        super(context, resId);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    protected void initTitleLayout() {
        leftView = getContentView().findViewById(R.id.ll_app_title_layout_left);
        contentView = (LinearLayout) getContentView().findViewById(R.id.ll_app_title_layout_center);
        contentView.addView(getAddContentView());

        leftView.setVisibility(View.VISIBLE);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mRightView = (CheckBox) getContentView().findViewById(R.id.cbx_app_title_layout_right);
    }

    public void setRightText(String text) {
        mRightView.setText(text);
    }

    public void setRightVisibly(boolean isVisibly) {
        if (isVisibly) {
            mRightView.setVisibility(View.VISIBLE);
        } else {
            mRightView.setVisibility(View.GONE);
        }
    }

    /**
     * 获取填充到标题栏中的View
     *
     * @return
     */
    public abstract View getAddContentView();

}
