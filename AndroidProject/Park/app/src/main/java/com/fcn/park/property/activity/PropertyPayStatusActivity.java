package com.fcn.park.property.activity;

import android.content.Context;
import android.content.Intent;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.PropertyPayStatusBinding;
import com.fcn.park.property.contract.PropertyPayStatusContract;
import com.fcn.park.property.presenter.PropertyPayStatusPresenter;


/**
 * 显示最终支付状态的页面
 */
public class PropertyPayStatusActivity extends BaseActivity<PropertyPayStatusBinding, PropertyPayStatusContract.View, PropertyPayStatusPresenter>
        implements PropertyPayStatusContract.View {

    public static void actionStart(Context context, boolean isPaySuccess, boolean isFromOrderList) {
        Intent intent = new Intent(context, PropertyPayStatusActivity.class);
        intent.putExtra(PAY_STATUS, isPaySuccess);
        intent.putExtra(IS_FROM_ORDER_LIST, isFromOrderList);
        context.startActivity(intent);
    }

    private static final String PAY_STATUS = "pay_status";
    private static final String IS_FROM_ORDER_LIST = "is_from_order_list";

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.property_pay_status_title));
        openTitleLeftView(true);
    }

    @Override
    public void onBackPressed() {
//        mPresenter.onClickBack();

    }

    @Override
    protected void initViews() {
        boolean booleanExtra = getIntent().getBooleanExtra(PAY_STATUS, false);
        mDataBinding.setPayStatus(true);
        mDataBinding.setPresenter(mPresenter);
        mDataBinding.propertyPayStatusNum.setText(getIntent().getStringExtra("PayNum"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.property_pay_status;
    }

    @Override
    protected PropertyPayStatusPresenter createPresenter() {
        return new PropertyPayStatusPresenter();
    }
}
