package com.fcn.park.property.activity;


import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.databinding.PropertyParkpayCheckStatusBinding;
import com.fcn.park.property.bean.PropertyParkPayBean;
import com.fcn.park.property.contract.PropertyParkPayCheckStatusContract;
import com.fcn.park.property.presenter.PropertyParkPayCheckStatusPresenter;


public class PropertyParkPayCheckStatusActivity extends BaseActivity<PropertyParkpayCheckStatusBinding,
        PropertyParkPayCheckStatusContract.View, PropertyParkPayCheckStatusPresenter> implements PropertyParkPayCheckStatusContract.View {

    private PropertyParkPayBean mPropertyParkPayBean;

    /**
     * 页面初始化
     */
    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        // TODO：区分审核中和审核未通过的状态
        mPresenter.getInitData(getIntent().getIntExtra(Constant.PROPERTY_PAY_ID, 0));
        mDataBinding.setParkPayBean(mPropertyParkPayBean);
    }

    /**
     * 页面初始化数据绑定
     *
     * @param PPBean
     */
    @Override
    public void setInitData(PropertyParkPayBean PPBean) {
        mPropertyParkPayBean = PPBean;
        ((EditText) findViewById(R.id.nameEdit)).setText(PPBean.getApplicantName());
        ((EditText) findViewById(R.id.companyEdit)).setText(PPBean.getCompany());
        ((EditText) findViewById(R.id.phoneEdit)).setText(PPBean.getPhone());
        ((EditText) findViewById(R.id.carNumber)).setText(PPBean.getCarNumber());
        Glide.with(this).load(ApiService.IMAGE_BASE + PPBean.getOnJobProImagePath()).into((ImageView) findViewById(R.id.onJobProImage));
        Glide.with(this).load(ApiService.IMAGE_BASE + PPBean.getDriverCardImagePath()).into((ImageView) findViewById(R.id.driverCardImage));
        Glide.with(this).load(ApiService.IMAGE_BASE + PPBean.getDriveringCardImagePath()).into((ImageView) findViewById(R.id.driveringCardImage));
        ((TextView) findViewById(R.id.applyType)).setText(PPBean.getApplyTypeInfo());
        ((EditText) findViewById(R.id.startDateEdit)).setText(PPBean.getStartDate() + "～" + PPBean.getEndDate());
        ((TextView) findViewById(R.id.reasonEdit)).setText(PPBean.getCheckinfo());
    }

    @Override
    protected PropertyParkPayCheckStatusPresenter createPresenter() {
        return new PropertyParkPayCheckStatusPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_parkpay_check_status;
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTitleText("月租车辆申请审核结果");
    }
}
