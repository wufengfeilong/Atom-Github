package com.fcn.park.base.utils;

import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class InputMethodUtils {

    /**
     * 开始软键盘
     * @param context
     */
    public static void openInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //2.切换软键盘的状态
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 判断软键盘是否打开
     *
     * @param rootView 最上层布局
     * @return 打开：true，隐藏：false
     */
    public static boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }
}
