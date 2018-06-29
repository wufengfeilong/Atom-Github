package com.fcn.park.base.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ChangeStatusBarStatus {

    public static boolean isStatusBarLight(int color) {
        float blue = Color.blue(color);
        float red = Color.red(color);
        float green = Color.green(color);

        double grayLevel = red * 0.299 + green * 0.587 + blue * 0.114;
        //通过RGB的颜色计算，判断是否属于浅色，当状态栏为浅色时设置字体颜色为深色
        //通过把 RGB 模式转换成 YUV 模式，而 Y 是明亮度（灰阶），因此只需要获得 Y 的值
        if (grayLevel >= 192) {
            return true;
        }
        return false;
    }

    /**
     * 设置状态栏的模式，会根据传递进行来的颜色进行判断，是否需要将颜色设置为黑色
     *
     * @param a
     * @param darkMode
     * @param color
     */
    public static void setStatusBarDarkMode(Activity a, boolean darkMode, int color) {

        float blue = Color.blue(color);
        float red = Color.red(color);
        float green = Color.green(color);

        double grayLevel = red * 0.299 + green * 0.587 + blue * 0.114;
        //通过RGB的颜色计算，判断是否属于浅色，当状态栏为浅色时设置字体颜色为深色
        //通过把 RGB 模式转换成 YUV 模式，而 Y 是明亮度（灰阶），因此只需要获得 Y 的值
        if (grayLevel >= 192) {
            setStatusBarDarkMode(a, darkMode);
        }
    }

    /**
     * 设置状态栏的模式，将状态栏的字体设置为黑色
     *
     * @param activity
     * @param darkmode
     * @return
     */
    public static boolean setStatusBarDarkMode(Activity activity, boolean darkmode) {

        boolean flag1;
        boolean flag2;

        flag1 = setMiuiStatusBarDarkMode(activity, darkmode);
        flag2 = setMeizuStatusBarDarkIcon(activity, darkmode);

        if (flag1 || flag2) {
            return true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setApi23StatusBarDarkMode(activity);
            return true;
        }
        return false;
    }


    /**
     * @param activity
     * @param darkmode
     * @return
     */
    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param activity
     * @param dark
     * @return
     */
    public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
            } catch (Exception e) {
            }
        }
        return result;
    }

    /**
     * 设置Android中Api23以上的状态栏白底黑字
     *
     * @param activity 当前的Activity
     */
    public static void setApi23StatusBarDarkMode(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

}
