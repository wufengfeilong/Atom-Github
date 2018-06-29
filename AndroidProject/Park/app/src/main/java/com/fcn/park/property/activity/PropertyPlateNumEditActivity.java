package com.fcn.park.property.activity;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.PropertyPlateNumEditBinding;
import com.fcn.park.property.contract.PropertyPlateNumEditContract;
import com.fcn.park.property.presenter.PropertyPlateNumEditPresenter;

/**
 * Created by 860115032 on 2018/04/19.
 */

public class PropertyPlateNumEditActivity extends BaseActivity<PropertyPlateNumEditBinding,
        PropertyPlateNumEditContract.View, PropertyPlateNumEditPresenter> implements PropertyPlateNumEditContract.View {


    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.property_temporary_park_plate_num_title));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {

        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_plate_num_edit;
    }

    @Override
    protected PropertyPlateNumEditPresenter createPresenter() {
        return new PropertyPlateNumEditPresenter();
    }

    @Override
    public String getPlateNum() {
        return mDataBinding.propertyPlateNumEdit.getText().toString();
    }
}
