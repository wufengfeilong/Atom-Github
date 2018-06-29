package com.fcn.park.me.activity;

import android.content.Intent;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.MeCarEditorActivityBinding;
import com.fcn.park.me.contract.MeCarEditorContract;
import com.fcn.park.me.presenter.MeCarEditorPresenter;

/**
 * 车辆编辑
 */
public class MeCarEditorActivity extends BaseActivity<MeCarEditorActivityBinding, MeCarEditorContract.View, MeCarEditorPresenter> implements MeCarEditorContract.View {

    @Override
    protected void setupTitle() {
        String titleStr = getString(R.string.me_car_editor);
        setTitleText(titleStr);
        openTitleLeftView(true);
    }

    @Override
    protected MeCarEditorPresenter createPresenter() {
        return new MeCarEditorPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        Intent intent = getPreIntent();
        String carOwner = intent.getStringExtra("carOwner");
        String plateNumber = intent.getStringExtra("plateNumber");
        String carPhone = intent.getStringExtra("carPhone");
        mDataBinding.carNameEditor.setText(carOwner);
        mDataBinding.carPlateNumber.setText(plateNumber);
        mDataBinding.carPhone.setText(carPhone);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.me_car_editor_activity;
    }

    @Override
    public String getCarOwner() {
        return mDataBinding.carNameEditor.getText().toString().trim();
    }

    @Override
    public String getPlateNumber() {
        return mDataBinding.carPlateNumber.getText().toString().trim();
    }

    @Override
    public String getPhone() {
        return mDataBinding.carPhone.getText().toString().trim();
    }

    @Override
    public Intent getPreIntent() {
        return getIntent();
    }

}
