package com.fcn.park.base.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.fcn.park.base.widget.TitleCompatLayout;

/**
 * 类描述：状态栏的兼容类，可以兼容到4.4以上的版本
 * <p>
 * ************使用方式***********
 * 》》》》》》特别注意，主题要设置为NoActionBar类型》》》》》
 * <p>
 * 1.在你原有的标题栏布局中包裹上TitleCompatLayout布局（就把它看作是一个包裹的布局就行，你原有的标题栏根本不用变）
 * 2.为它设置上两个属性，必须设置这个两个属性（1.宽高分别设置为MATCH_PARENT和WRAP_CONTENT,2.设置背景颜色，一定要给它设置上背景颜色,当然如果不设置的话，会显示默认的）
 * 3.在java代码中直接调用静态方法 StatusBarCompat.setStatusBarColor(this, titleCompatLayout);
 * <p>
 * ************结束方式***********
 */
public class StatusBarCompat {

    private static final String TAG = StatusBarCompat.class.toString();

    /**
     * 会根据设置进来的颜色自动变色（并且如果状态栏的颜色为高亮时，会在MiUi或者是魅族手机或者6.0以上的手机设置白底黑字）
     *
     * @param activity 当前的Activity
     * @param color    要设置的颜色
     * @param alpha    颜色的透明度
     */
    public static void setStatusBarColor(Activity activity, int color, int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;
        //根据传入的透明度，重新计算颜色值
        color = Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
        boolean isLightColor = ChangeStatusBarStatus.isStatusBarLight(color);
        int opsion = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        if (ChangeStatusBarStatus.setMiuiStatusBarDarkMode(activity, isLightColor)) {

        } else if (ChangeStatusBarStatus.setMeizuStatusBarDarkIcon(activity, isLightColor)) {

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        }
    }


    /**
     * 推荐使用{@link #setStatusBarColor(Activity, int, int)}
     */
    @Deprecated
    public static void setStatusBarColor(Activity activity, TitleCompatLayout titleLayout, boolean isAddStatusBar) {

        if (activity == null) {
            Log.e(TAG, "activity or titleLayout null");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(activity, true);
            //AndroidBug5497Workaround.assistActivity(activity);

            if (isAddStatusBar && titleLayout != null) {
                View statusBar = titleLayout.getChildAt(0);
                setStatusBarColor(activity, titleLayout, statusBar);
            }


        }

    }


    private static void setStatusBarColor(Activity activity, TitleCompatLayout titleLayout, View statusBar) {

        ColorDrawable drawable = (ColorDrawable) titleLayout.getBackground();
        if (drawable == null) {
            drawable = new ColorDrawable();
        }

        ChangeStatusBarStatus.setStatusBarDarkMode(activity, true, drawable.getColor());

        statusBar.getLayoutParams().height = SystemUtils.getStatusHeight(activity);
        statusBar.setLayoutParams(statusBar.getLayoutParams());
        statusBar.setBackgroundColor(drawable.getColor());


    }

    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
