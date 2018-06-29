package com.fcn.park.manager.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.manager.bean.ManagerAdvertisingFeeEditBean;
import com.fcn.park.manager.contract.ManagerAdvertisingFeeEditContract;
import com.fcn.park.manager.module.ManagerAdvertisingFeeEditModule;

import java.util.List;

/**
 * 广告费用编辑用Presenter.
 */
public class ManagerAdvertisingFeeEditPresenter
        extends BasePresenter<ManagerAdvertisingFeeEditContract.View>
        implements ManagerAdvertisingFeeEditContract.Presenter {

    private String TAG = "=== ManagerAdvertisingFeeEditPresenter ===";
    private ManagerAdvertisingFeeEditModule mManagerAdvertisingFeeEditModule;

    @Override
    public void attach(ManagerAdvertisingFeeEditContract.View view) {
        super.attach(view);
        mManagerAdvertisingFeeEditModule = new ManagerAdvertisingFeeEditModule();
    }

    /**
     * 向服务器请求数据
     */
    @Override
    public void loadAdvertisingFeeInfo() {
        mManagerAdvertisingFeeEditModule.getAdvertisingSetFeeData(getView().getContext(), new OnDataCallback<List<ManagerAdvertisingFeeEditBean.AdvertisingFeeList>>() {
            @Override
            public void onSuccessResult(List<ManagerAdvertisingFeeEditBean.AdvertisingFeeList> listData) {
                // 通过showDataToView()这个方法，将从服务器取得的广告套餐数据显示到画面上
                getView().showDataToView(listData);
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast(errMsg);
            }
        });
    }

    /**
     * 点击“确定”按钮后，将设置的广告费用的套餐数据上传至服务器
     */
    @Override
    public void advertisingFeeSubmitButton() {

        // 获取画面上输入的套餐费用的值
        String inputOffer1Fee = getView().getInputOffer1Fee();
        String inputOffer2Fee = getView().getInputOffer2Fee();
        String inputOffer3Fee = getView().getInputOffer3Fee();

        Log.d(TAG, "===================== inputOffer1Fee = " + inputOffer1Fee);
        Log.d(TAG, "===================== inputOffer2Fee = " + inputOffer2Fee);
        Log.d(TAG, "===================== inputOffer3Fee = " + inputOffer3Fee);
        if (TextUtils.isEmpty(inputOffer1Fee)) {
            getView().showToast("请输入套餐一的费用");
            return;
        }

        if (TextUtils.isEmpty(inputOffer2Fee)) {
            getView().showToast("请输入套餐二的费用");
            return;
        }

        if (TextUtils.isEmpty(inputOffer3Fee)) {
            getView().showToast("请输入套餐三的费用");
            return;
        }

        // 点击“确定”按钮后，将设置的广告费用的套餐数据上传至服务器
        mManagerAdvertisingFeeEditModule.updateAdvertisingSetFeeData(getView().getContext(), inputOffer1Fee, inputOffer2Fee, inputOffer3Fee, new OnDataCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().showToast(data);
                getView().closeActivity();
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast(errMsg);
            }
        });
    }
}
