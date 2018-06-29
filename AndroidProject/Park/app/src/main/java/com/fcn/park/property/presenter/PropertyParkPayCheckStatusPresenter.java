package com.fcn.park.property.presenter;

import android.content.Intent;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.property.activity.PropertyParkPayActivity;
import com.fcn.park.property.bean.PropertyParkPayBean;
import com.fcn.park.property.contract.PropertyParkPayCheckStatusContract;
import com.fcn.park.property.module.ParkPayModule;

/**
 * Created by 860115032 on 2018/04/08.
 */
public class PropertyParkPayCheckStatusPresenter
        extends BasePresenter<PropertyParkPayCheckStatusContract.View>
        implements PropertyParkPayCheckStatusContract.Presenter {

    private ParkPayModule parkPayModule;

    @Override
    public void attach(PropertyParkPayCheckStatusContract.View view) {
        super.attach(view);
        parkPayModule = new ParkPayModule();
    }

    /**
     * 初始化数据获取
     *
     * @param parkPayId
     */
    @Override
    public void getInitData(int parkPayId) {

        parkPayModule.getParkPayRejectInitData(getView().getContext(), parkPayId, new OnDataCallback<PropertyParkPayBean>() {
            @Override
            public void onSuccessResult(PropertyParkPayBean data) {
                getView().setInitData(data);
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast(errMsg);
            }
        });
    }

    /**
     * 重新申请
     */
    @Override
    public void onClickApplyCommit() {
        Intent intent = new Intent(getView().getContext(), PropertyParkPayActivity.class);
        getView().getContext().startActivity(intent);
    }
}





