package com.fcn.park.login;

import android.content.Context;
import android.util.Log;

import com.fcn.park.login.bean.User;
import com.fcn.park.login.util.UserUtils;

import java.lang.ref.WeakReference;

/**
 * 用户登录帮助类
 * Created by 860115001 on 2018/04/10.
 */

public class LoginHelper {

    private static LoginHelper sInstance;
    private WeakReference<Context> mContext;
    private User mUserBean;
    private boolean isOnline;

    private LoginHelper() {
    }

    /**
     * 获取类实例
     * @return
     */
    public static LoginHelper getInstance() {
        if (sInstance == null) {
            sInstance = new LoginHelper();
        }
        return sInstance;
    }

    /**
     * 初始化
     * @param context
     * @return
     */
    public LoginHelper init(Context context) {
        mContext = new WeakReference<>(context);
        //判断用户是否登录
        isOnline = checkIsOnline();
        return this;
    }

    /**
     * 判断用户是否登录
     * @return
     */
    private boolean checkIsOnline() {
        boolean isOnline;
        User user = UserUtils.getUser(mContext.get());
        if (user.getToken() != null) {
            isOnline = true;
            Log.d(LoginHelper.class.getSimpleName(),"--用户id为" + user.getToken());
        } else {
            System.out.println("--用户对象为空");
            isOnline = false;
        }
        if (isOnline) {
            mUserBean = user;
        }
        return isOnline;
    }

    /**
     * 用户退出
     * @return
     */
    public boolean userExit() {
        mUserBean = null;
        isOnline = false;
        return UserUtils.quit(mContext.get());
    }

    /**
     * 获取UserBean
     * @return
     */
    public User getUserBean() {
        if (mUserBean == null)
            return new User();
        return mUserBean;
    }

    /**
     * 获取用户Id
     * @return
     */
    public String getUserToken() {
        if (mUserBean != null)
            return mUserBean.getToken();
        return "";
    }


    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        if (online) {
            mUserBean = UserUtils.getUser(mContext.get());
        } else {
            userExit();
        }
        isOnline = online;
    }
}
