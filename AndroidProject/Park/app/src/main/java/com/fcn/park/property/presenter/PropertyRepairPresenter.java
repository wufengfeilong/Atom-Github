package com.fcn.park.property.presenter;

import android.text.TextUtils;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.property.bean.PropertyRepairBean;
import com.fcn.park.property.contract.PropertyRepairContract;
import com.fcn.park.property.module.PropertyRepairModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;


/**
 * Created by 860117073 on 2018/4/11.
 */

/**
 * 报修的presenter
 */

public class PropertyRepairPresenter extends BasePresenter<PropertyRepairContract.View> implements PropertyRepairContract.Presenter {
    private PropertyRepairModule mPropertyRepairModule;
    @Override
    public void attach(PropertyRepairContract.View view) {
        super.attach(view);
        mPropertyRepairModule = new PropertyRepairModule();
    }
    @Override
    public void onClickSubmit() {

        final PropertyRepairBean bean = new PropertyRepairBean();
        final Map<String, String> imageMap = getView().getImageMap();
        final String userId=LoginHelper.getInstance().getUserToken();
        final String repairName = getView().getRepairName();
        final String repairPhone = getView().getRepairPhone();
        final String repairAddress = getView().getRepairAddress();
        final String repairContent = getView().getRepairContent();

        //检查用户输入的check
        if (TextUtils.isEmpty(repairName)) {
            getView().showToast("请输入姓名");
        } else if (TextUtils.isEmpty(repairPhone)) {
            getView().showToast("请输入电话号");
        } else if (TextUtils.isEmpty(repairAddress)) {
            getView().showToast("请输入地址");
        } else if (TextUtils.isEmpty(repairContent)) {
            getView().showToast("请描述您的问题");
        } else {
            bean.setUserId(userId);
            bean.setRepairName(repairName);
            bean.setRepairPhone(repairPhone);
            bean.setRepairAddress(repairAddress);
            bean.setRepairContent(repairContent);

            //将bean存到Map对象中
            final Map<String ,String> reqMap = ApiClient.createBodyMap(bean);

            //将图片路径保存到List中
            ArrayList<String> pictureList = new ArrayList<String>();
            if (imageMap.get("repairPicture1") != null) {
                pictureList.add(imageMap.get("repairPicture1"));
            }

            if (imageMap.get("repairPicture2") != null) {
                pictureList.add(imageMap.get("repairPicture2"));
            }

            if (imageMap.get("repairPicture3") != null) {
                pictureList.add(imageMap.get("repairPicture3"));
            }

            final List<MultipartBody.Part> parts = new ArrayList<>();
            //判断用户是否上传了图片
            if (pictureList.size() == 0) {
                getView().showToast("请至少上传一张图片");
            } else {

                ApiClient.compressMore(getView().getContext(), pictureList, new OnDataCallback<List<File>>() {
                    @Override
                    public void onSuccessResult(List<File> fileList) {
                        for (int i = 0; i < fileList.size(); i++) {
                            parts.add(ApiClient.getFileBody("repairPicture" + (i + 1), fileList.get(i)));
                        }

                        //提交报修内容
                        mPropertyRepairModule.requestSendRepairInformation(getView().getContext(), parts, reqMap, new OnDataCallback<String>() {
                            @Override
                            public void onSuccessResult(String s) {
                                getView().showToast("报修成功");
                                getView().closeActivity();
                            }
                            @Override
                            public void onError(String errMsg) {
                                getView().showToast("报修失败");
                            }
                        });
                    }
                    @Override
                    public void onError(String errMsg) {

                    }
                });
            }
        }
    }}


