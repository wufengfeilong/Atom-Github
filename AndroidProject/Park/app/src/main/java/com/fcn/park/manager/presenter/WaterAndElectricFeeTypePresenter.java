package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.manager.activity.ElectricFeeListActivity;
import com.fcn.park.manager.activity.WaterFeeListActivity;
import com.fcn.park.manager.contract.WaterAndElectricFeeTypeContract;

/**
 * 水电费分支用Presenter.
 */
public class WaterAndElectricFeeTypePresenter
        extends BasePresenter<WaterAndElectricFeeTypeContract.View>
        implements WaterAndElectricFeeTypeContract.Presenter {

    @Override
    public void goWaterFeeList() {// 水费列表画面
        getView().actionStartActivity(WaterFeeListActivity.class);
    }

    @Override
    public void goElectricFeeList() {// 电费列表画面
        getView().actionStartActivity(ElectricFeeListActivity.class);
    }
}
