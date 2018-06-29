package com.fcn.park.me.activity;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.MeAddCarActivityBinding;
import com.fcn.park.me.contract.MeAddCarContract;
import com.fcn.park.me.presenter.MeAddCarPresenter;
/**
 * 车辆追加
 */

public class MeAddCarActivity extends BaseActivity<MeAddCarActivityBinding,MeAddCarContract.View,MeAddCarPresenter> implements MeAddCarContract.View{

    @Override
    protected void setupTitle() {
        String titleStr  = getString(R.string.me_car_add);
        setTitleText(titleStr);
        openTitleLeftView(true);
    }
    @Override
    protected MeAddCarPresenter createPresenter(){
        return new MeAddCarPresenter();
    }
    @Override
    protected void initViews(){
        mDataBinding.setPresenter(mPresenter);
    }
    @Override
    protected int getLayoutId(){
        return R.layout.me_add_car_activity;
    }
    @Override
    public String getCarOwner(){
        return mDataBinding.carNameAdd.getText().toString().trim();
    }
    @Override
    public String getPlateNumber(){
        return mDataBinding.carPlateNumberAdd .getText().toString().trim();
    }
    @Override
    public String getPhone(){
        return mDataBinding.carPhoneAdd.getText().toString().trim();
    }

}
