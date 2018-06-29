package com.fcn.park.property.activity;

import android.util.Log;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.PropertyPaymentDetailBinding;
import com.fcn.park.property.bean.PropertyMainBean;
import com.fcn.park.property.contract.PropertyPaymentDetailContract;
import com.fcn.park.property.presenter.PropertyPaymentDetailPresenter;

/**
 * 缴费详情画面
 */

public class PropertyPaymentDetailActivity
        extends BaseActivity<PropertyPaymentDetailBinding, PropertyPaymentDetailContract.View, PropertyPaymentDetailPresenter>
        implements PropertyPaymentDetailContract.View {

    private String TAG = "PropertyPaymentDetailActivity";

    private PropertyMainBean bean = null;

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.property_payment_detail_title));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        Log.d(TAG, "initViews  bean.getPayType() = " + bean.getPayType());

        // 将前画面传来的bean数据同步到画面显示
        mDataBinding.setBean(bean);
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        // 获取前画面传来的数据
        bean = (PropertyMainBean) getIntent().getSerializableExtra("bean");
        Log.d(TAG, "getLayoutId  bean.getPayType() = " + bean.getPayType());
        return R.layout.property_payment_detail;
    }

    @Override
    protected PropertyPaymentDetailPresenter createPresenter() {
        return new PropertyPaymentDetailPresenter();
    }
}
