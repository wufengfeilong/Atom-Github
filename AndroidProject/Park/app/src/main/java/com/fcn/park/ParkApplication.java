package com.fcn.park;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.apkfuns.logutils.LogUtils;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.http.RetrofitManager;
import com.fcn.park.getui_push.DemoIntentService;
import com.fcn.park.getui_push.DemoPushService;
import com.fcn.park.login.LoginHelper;
import com.igexin.sdk.PushManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

/**
 * Created by 860115001 on 2018/04/11.
 */

public class ParkApplication extends Application {

    private static ParkApplication parkApplicationApl;
    private IWXAPI msgApi;

    public IWXAPI getWxApi() {
        return msgApi;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        parkApplicationApl = this;
        try {
            msgApi = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, false);
            // 将该app注册到微信
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        // com.getui.demo.DemoPushService 为第三方自定义推送服务
        PushManager.getInstance().initialize(parkApplicationApl, DemoPushService.class);
        // com.getui.demo.DemoIntentService 为第三方自定义的推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(parkApplicationApl, DemoIntentService.class);
        LogUtils.getLogConfig()
                .configAllowLog(true)
                .configTagPrefix("HttpLogo")
                .configShowBorders(true)
                .configFormatTag("%d{HH:mm:ss:SSS} %t %c{-5}");
        LoginHelper.getInstance().init(this);
        RetrofitManager.getInstance().init(this);
    }

    public static ParkApplication getInstance() {
        return parkApplicationApl;
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
