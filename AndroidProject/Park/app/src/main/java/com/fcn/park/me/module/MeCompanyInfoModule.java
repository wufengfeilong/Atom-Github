package com.fcn.park.me.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.view.menu.MenuItemImpl;
import android.util.Log;
import com.fcn.park.R;
import com.fcn.park.base.http.*;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.me.MenuModuleIml;
import com.fcn.park.me.bean.InputMenuBean;
import com.fcn.park.me.bean.MePersonInfoBean;
import com.fcn.park.me.bean.PictureBean;
import okhttp3.MultipartBody;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/16
 * time      15:59
 * 个人中心-企业信息编辑
 */
public class MeCompanyInfoModule extends MenuModuleIml<InputMenuBean> {
    private MePersonInfoBean mePersonInfoBean;
    /**
     * 上传头像
     */
    public void uploadUserAvatar(Context context, String id, File avatar, final OnDataGetCallback<String> callback) {
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
     * 上传公司信息
     * @param userId
     * @param callback
     */
    public void updateCompanyInfo(Context context, String userId, MePersonInfoBean bean, final OnDataGetCallback<Boolean> callback) {

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
     * 获取公司信息
     */
    public void requestCompanyInfo(Context context, String userId, final OnDataGetCallback<MePersonInfoBean> callback) {
        RetrofitManager.toSubscribe(
                ApiClient.getApiService().getPersonInfo(userId)
                , new ProgressSubscriber<HttpResult<MePersonInfoBean>>(context, new RequestImpl<HttpResult<MePersonInfoBean>>() {
                    @Override
                    public void onNext(HttpResult<MePersonInfoBean> result) {
                        mePersonInfoBean = result.getData();
                        if (mePersonInfoBean != null)
                            callback.onSuccessResult(mePersonInfoBean);
                    }
                })
        );
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
        if (menuItem.getItemId() == R.id.company_menu_item_avatar) {
            bean.setEdit(true);
        }
        bean.setNeedInput(menuItem.isEnabled());
        return bean;
    }
    /**
     * 保存轮播图片信息
     */
    public void saveBannersInfo(final Context context, List<MultipartBody.Part> parts, Map reqJson, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().saveBannersInfo(parts, reqJson),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                }));
    }
    /**
     * 更新公司简介
     */
    public void updateCompanyIntroduce(final Context context, String userId, String introduce, final OnDataCallback<String> callback) {
        RetrofitManager.toSubscribe(ApiClient.getApiService().updateCompanyIntroduce(userId, introduce),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                }));
    }
    /**
     * 加载公司简介
     */
    public void loadIntroduceInfo(final Context context, String userId,  final OnDataCallback<String> callback) {

        RetrofitManager.toSubscribe(ApiClient.getApiService().getCompanyIntroduceInfo(userId),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<String>>() {
                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        } else {
                            callback.onError(stringHttpResult.getMsg());
                        }
                    }
                }));
    }
    /**
     * 获取轮播图片
     */
    public void loadPictures(final Context context, String userId,  final OnDataGetCallback<List<PictureBean>> callback) {

        RetrofitManager.toSubscribe(ApiClient.getApiService().getCompanyPictureInfo(userId),
                new ProgressSubscriber<>(context, new RequestImpl<HttpResult<List<PictureBean>>>() {
                    @Override
                    public void onNext(HttpResult<List<PictureBean>> stringHttpResult) {
                        if (stringHttpResult.isResult()) {
                            callback.onSuccessResult(stringHttpResult.getData());
                        }
                    }
                }));
    }


}
