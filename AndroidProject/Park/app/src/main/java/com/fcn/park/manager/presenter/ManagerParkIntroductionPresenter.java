package com.fcn.park.manager.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.OnDataGetCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.manager.bean.ManagerParkIntroductionBean;
import com.fcn.park.manager.contract.ManagerParkIntroductionContract;
import com.fcn.park.manager.module.ManagerParkIntroductionModule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * 园区管理用Presenter.
 */
public class ManagerParkIntroductionPresenter
        extends BasePresenter<ManagerParkIntroductionContract.View>
        implements ManagerParkIntroductionContract.Presenter {

    private String TAG = "=== ManagerParkIntroductionPresenter ===";
    private ManagerParkIntroductionModule mManagerParkIntroductionModule;

    @Override
    public void attach(ManagerParkIntroductionContract.View view) {
        super.attach(view);
        mManagerParkIntroductionModule = new ManagerParkIntroductionModule();
    }

    @Override
    public void loadInfo(String parkId) {
        mManagerParkIntroductionModule.getParkIntroductionInfo(getView().getContext(), parkId, new OnDataGetCallback<ManagerParkIntroductionBean>() {
            @Override
            public void onSuccessResult(ManagerParkIntroductionBean data) {
                getView().updateInfo(data);
            }
        });
    }

    @Override
    public void onClickPoseParkIntroduction() {

        final ManagerParkIntroductionBean parkBean = getView().getParkBean();
        final Map<String, String> imageMap = getView().getImageMap();

        parkBean.setParkId(getView().getInputParkId());
        parkBean.setParkName(getView().getInputParkName());
        parkBean.setParkContent(getView().getInputParkContent());
        parkBean.setParkAddress(getView().getInputParkAddress());
        parkBean.setParkTelephone(getView().getInputParkTelephone());
        parkBean.setUpdateUser(LoginHelper.getInstance().getUserToken());
        parkBean.setInsertUser(LoginHelper.getInstance().getUserToken());

        final String parkId = getView().getInputParkId();
        Log.d(TAG, "=========== parkId = " + parkId);
        final String parkName = getView().getInputParkName();
        Log.d(TAG, "=========== parkName = " + parkName);
        final String parkContent = getView().getInputParkContent();
        Log.d(TAG, "=========== parkContent = " + parkContent);
        final String parkAddress = getView().getInputParkAddress();
        Log.d(TAG, "=========== parkAddress = " + parkAddress);
        final String parkTelephone = getView().getInputParkTelephone();
        Log.d(TAG, "=========== parkTelephone = " + parkTelephone);

        if (getView().checkInputParkNameEmpty()) {
            getView().showToast("请输入园区名称");
            return;
        }

        if (getView().checkInputParkContentEmpty()) {
            getView().showToast("请输入园区简介内容");
            return;
        }

        if (getView().checkInputParkAddressEmpty()) {
            getView().showToast("请输入园区所在地址");
            return;
        }
        if (getView().checkInputParkTelephoneEmpty()) {
            getView().showToast("请输入园区联系人电话");
            return;
        }

        Map<String, String> reqMap = ApiClient.createBodyMap(parkBean);

        MultipartBody.Part img1 = ApiClient.getFileBody("img1", new File(imageMap.get("img1")));
        MultipartBody.Part img2 = ApiClient.getFileBody("img2", new File(imageMap.get("img2")));
        MultipartBody.Part img3 = ApiClient.getFileBody("img3", new File(imageMap.get("img3")));
        Log.d("==========aaaaaa=======", " img1 = " + new File(imageMap.get("img1")).getName());
        Log.d("==========aaaaaa=======", " img2 = " + new File(imageMap.get("img2")).getName());
        Log.d("==========aaaaaa=======", " img3 = " + new File(imageMap.get("img3")).getName());
        List<MultipartBody.Part> parts = new ArrayList<>();

        if (new File(imageMap.get("img1")).getName() != null) {
            parts.add(img1);
        }
        if (new File(imageMap.get("img2")).getName() != null) {
            parts.add(img2);
        }
        if (new File(imageMap.get("img3")).getName() != null) {
            parts.add(img3);
        }

        mManagerParkIntroductionModule.uploadParkInfoImg(getView().getContext(), parts, reqMap, new OnDataCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().showToast("提出成功 data = " + data);
                SharedPreferences preferences= getView().getContext().getSharedPreferences("park_info", Context.MODE_PRIVATE);
                String park_id = preferences.getString("park_id", "0");//第二个参数表示如果没有找到，则使用该默认值
                if (!park_id.equals(data) && Integer.valueOf(data) > 0) {
                    SharedPreferences.Editor editor= getView().getContext().getSharedPreferences("park_info", Context.MODE_PRIVATE).edit();//file_name即为文件名
                    editor.putString("park_id", data);
                    editor.commit();//将数据持久化到存储介质中去
                }

                Log.d(TAG, "============ data = " + data);
                getView().closeActivity();
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast("提出失败，请重新提交");
            }
        });
    }
}