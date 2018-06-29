package com.fcn.park.me.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.view.menu.MenuItemImpl;
import android.util.Log;
import com.fcn.park.R;
import com.fcn.park.base.http.*;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.me.MenuModuleIml;
import com.fcn.park.me.bean.InputMenuBean;
import com.fcn.park.me.bean.MePersonInfoBean;

import java.io.File;
import java.util.List;

/**
 * create by 860115039
 * date      2018/04/16
 * time      15:59
 * 个人中心-个人信息编辑
 */
public class MePersonInfoModule  extends MenuModuleIml<InputMenuBean> {
    private MePersonInfoBean mPersonInfoBean;
    /**
     * 上传头像
     */
    public void uploadUserAvatar(Context context,String id, File avatar, final OnDataGetCallback<String> callback) {
        Log.d("upload", "-----开始上传头像-------");
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().uploadAvatar(ApiClient.getFileBody("userPicture", avatar), id)
                , new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> result) {
                        callback.onSuccessResult(result.getData());
                    }
                })
        );
    }

    /**
     * 更新个人信息
     * @param userId
     * @param callback
     */
    public void updatePersonInfo(Context context, String userId, MePersonInfoBean bean, final OnDataGetCallback<Boolean> callback) {

        RetrofitManager.toSubscribe(
                ApiClient.getApiService().updatePersonInfo(
                        LoginHelper.getInstance().getUserToken()
                        , bean.getName()
                        , bean.getContactInfo()
                )
                , new ProgressSubscriber<HttpResult<String>>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.isResult());
                        } else {
                            //callback.onError(stringHttpResult.getMsg());
                        }
                    }
                })
        );
    }
    /**
     * 获取个人信息
     */
    public void requestPersonInfo(Context context, String userId, final OnDataGetCallback<MePersonInfoBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getPersonInfo(userId)
                , new ProgressSubscriber<HttpResult<MePersonInfoBean>>(context, new RequestImpl<HttpResult<MePersonInfoBean>>() {
                    @Override
                    public void onNext(HttpResult<MePersonInfoBean> result) {
                        mPersonInfoBean = result.getData();
                        if (mPersonInfoBean != null)
                        callback.onSuccessResult(mPersonInfoBean);
                    }
                })
        );
    }
    /**
     * 获取个人信息bean
     */
    public MePersonInfoBean getPersonInfoBean() {
        return mPersonInfoBean;
    }
    /**
     * 关联menu列表
     */
    @Override
    public void attachMenuList(List<MenuItemImpl> menuItemList) {
        super.attachMenuList(menuItemList);
        InputMenuBean bean = mMenuBeanList.get(0);
        bean.setImage(true);
    }
    /**
     * 获取菜单bean
     */
    @SuppressLint("RestrictedApi")
    @Override
    public InputMenuBean getMenuBean(MenuItemImpl menuItem) {
        InputMenuBean bean = new InputMenuBean();
        if (menuItem.getItemId() == R.id.person_menu_item_avatar) {
            bean.setEdit(true);
        }
        bean.setNeedInput(menuItem.isEnabled());
        return bean;
    }
}
