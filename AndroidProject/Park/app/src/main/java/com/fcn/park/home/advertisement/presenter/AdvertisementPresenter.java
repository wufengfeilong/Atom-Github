package com.fcn.park.home.advertisement.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.home.advertisement.contract.AdvertisementContract;
import com.fcn.park.home.advertisement.module.AdvertisementModule;
import com.fcn.park.login.LoginHelper;

import java.io.File;

import okhttp3.MultipartBody;
import top.zibin.luban.OnCompressListener;

/**
 * Created by 860115001 on 2018/04/24.
 */

public class AdvertisementPresenter extends BasePresenter<AdvertisementContract.View> implements AdvertisementContract.Presenter {

    private AdvertisementModule advertisementModule;

    @Override
    public void attach(AdvertisementContract.View view) {
        super.attach(view);
        advertisementModule = new AdvertisementModule();
    }

    /**
     * 添加广告图片
     */
    @Override
    public void addAdvertisement() {
        getView().openSelectPhotoPop();
    }

    /**
     * 提交广告位
     */
    @Override
    public void onSubmit() {
        File uploadFile = new File(getView().getPictureUrl());
        ApiClient.pictureCompressionWhiteCheck(getView().getContext(), uploadFile, new OnCompressListener() {
            @Override
            public void onStart() {
                getView().showProgressDialog("图片上传中...");
            }

            @Override
            public void onSuccess(File file) {
                getView().hindProgressDialog();

            }

            @Override
            public void onError(Throwable e) {
                getView().hindProgressDialog();
                getView().showProgressDialog("图片上传失败");
            }
        });
        MultipartBody.Part part = ApiClient.getFileBody("advertisementPicture", new File(getView().getPictureUrl()));
        advertisementModule.submitAdvertisement(getView().getContext(), part, getView().getContent(), getView().getAdvertisementPayType(),
                LoginHelper.getInstance().getUserBean().getToken(), new OnDataCallback<String>() {
            @Override
            public void onSuccessResult(String msg) {
                getView().showToast(msg);
                getView().closeActivity();
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast(errMsg);
            }
        });
    }
}
