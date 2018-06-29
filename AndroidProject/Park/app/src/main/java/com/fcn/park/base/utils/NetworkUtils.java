package com.fcn.park.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by 860115001 on 2018/04/08.
 * 类描述：网络诊断工具类
 */

public class NetworkUtils {

    private NetworkUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static final int ACTION_WIFI_SETTINGS = 0;
    public static final int ACTION_DATA_ROAMING_SETTINGS = 1;

    /**
     * 判断网络是否可用，需要加上访问网络状态的权限
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvaiable(Context context) {
        ConnectivityManager connectivity = getConnectivityManager(context);
        if (connectivity == null) {
            return false;
        }
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (info == null || !info.isAvailable()) {
            return false;
        }
        return true;
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = getConnectivityManager(context);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是WiFi网络
     *
     * @param context
     * @return
     */
    public static boolean isWifiConn(Context context) {
        boolean isCommected = isConnected(context);
        if (isCommected) {
            ConnectivityManager connectivity = getConnectivityManager(context);
            if (connectivity == null)
                return false;
            return connectivity.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }

    /**
     * 获取网络连接管理
     *
     * @param context
     * @return
     */
    private static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 打开网络设置界面 整体
     *
     * @param activity
     */
    public static void openSetting(Activity activity) {
        //整体
        activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    /**
     * 打开网络设置界面 WIFI/流量
     *
     * @param activity
     * @param i        0:WIFI/1:流量
     */
    public static void openSetting(Activity activity, int i) {
        if (i == ACTION_WIFI_SETTINGS) {
            //WIFI
            activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        } else if (i == ACTION_DATA_ROAMING_SETTINGS) {
            //流量
            activity.startActivity(new Intent(
                    Settings.ACTION_DATA_ROAMING_SETTINGS));
        }
    }


    public static String getErrorMsg(Throwable e) {
        String errorMsg = "网络数据异常";
        if (e instanceof SocketTimeoutException) {
            errorMsg = "网络请求超时，请重试";
        } else if (e instanceof ConnectException) {
            errorMsg = "服务器连接异常";
        } else if (e instanceof JsonSyntaxException) {
            errorMsg = "网络数据类型转换异常";
        } else if (e instanceof NullPointerException) {
            errorMsg = "网络数据为空";
        }else if (e instanceof FileNotFoundException){
            errorMsg = "上传文件路径未找到";
        }
        return errorMsg;
    }
}
