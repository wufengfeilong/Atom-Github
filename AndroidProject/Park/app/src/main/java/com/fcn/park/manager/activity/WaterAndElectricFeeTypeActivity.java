package com.fcn.park.manager.activity;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.WaterAndElectricFeeTypeBinding;
import com.fcn.park.manager.contract.WaterAndElectricFeeTypeContract;
import com.fcn.park.manager.presenter.WaterAndElectricFeeTypePresenter;

public class WaterAndElectricFeeTypeActivity
        extends BaseActivity<WaterAndElectricFeeTypeBinding, WaterAndElectricFeeTypeContract.View, WaterAndElectricFeeTypePresenter>
        implements WaterAndElectricFeeTypeContract.View {

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_water_and_electric_fee_type_title));
        openTitleLeftView(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.water_and_electric_fee_type;
    }

    @Override
    protected void initViews() {
        mDataBinding.setWaterAndElectricFeeTypePresenter(mPresenter);
    }

    @Override
    protected WaterAndElectricFeeTypePresenter createPresenter() {
        return new WaterAndElectricFeeTypePresenter();
    }
}