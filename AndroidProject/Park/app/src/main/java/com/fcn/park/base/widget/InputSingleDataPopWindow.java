package com.fcn.park.base.widget;

import android.app.Activity;

/**
 * Created by lvzp on 201/3/16.
 * 类描述：
 */

public class InputSingleDataPopWindow extends InputDataPopWindow {

    /**
     * 初始化一个PopupWindow
     *
     * @param context 上下文对象
     */
    public InputSingleDataPopWindow(final Activity context) {
        super(context);
        mInputEdit.setSingleLine();
        isSingleLine = true;
    }

    public void setInputHintText(String hintText) {
        mInputEdit.setHint(hintText);
    }

}
