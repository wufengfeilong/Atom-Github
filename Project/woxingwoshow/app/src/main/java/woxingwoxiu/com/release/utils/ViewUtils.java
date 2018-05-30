package woxingwoxiu.com.release.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;

import java.lang.reflect.Method;
import java.util.List;

/**
 * View utils
 */

public class ViewUtils {
    private static String TAG = "ViewUtils";

    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        Log.d(TAG, className + " is not Foreground");

        return false;
    }
    public static void setNavigationBar(Context context, View view) {
        Resources resources = context.getResources();
        // 用来拿到导航栏的高度
        int resourceId1 = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        // 用来拿到是否有虚拟导航栏
        int resourceId2 = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId2 != 0) {

            // 判断手机是否有虚拟的导航栏
            boolean hasNav = resources.getBoolean(resourceId2);
            // 如果手机有虚拟导航栏（即导航栏是在屏幕上，跟布局重叠在一起，而不是在手机屏幕外的地方）
            if (hasNav) {
                //获取NavigationBar的高度(获取底部导航栏的高度)
                int height = resources.getDimensionPixelSize(resourceId1);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = height;
                view.setLayoutParams(layoutParams);
            }
            // 如果手机没有虚拟导航栏（导航栏在屏幕外，但是导航栏仍然是有的。）
            else {
                view.setVisibility(View.GONE);
            }

        }
    }

    private int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        int id = context.getResources().getIdentifier("navigation_bar_height",
                "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = context.getResources().getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }
    /**
     * 获取是否存在NavigationBar
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        try {
            Resources rs = context.getResources();
            int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id);
            }
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    /**
     * 获取底部导航栏高度，没有底部导航栏的话，返回0
     * @param context
     * @return
     */
    public static int getDaoHangHeight(Context context) {
        int resourceId = 0;
        boolean hasMenuKey = ViewConfiguration.get(context)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);
        int sdkint = android.os.Build.VERSION.SDK_INT;
        if (!hasMenuKey && !hasBackKey && sdkint>19) {
            // 这个设备有一个导航栏
            int rid = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
            if (rid != 0) {
                resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
                return context.getResources().getDimensionPixelSize(resourceId);
            } else
                return 0;
        }
        return 0;
    }

    public static void setImgTransparent(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                            | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  //不隐藏和透明虚拟导航栏  因为会遮盖底部的布局
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE//保持布局状态
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);//不隐藏和透明虚拟导航栏  因为会遮盖底部的布局

        }
    }
}
