package com.fcn.park.property.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.databinding.PropertyPayActivityLayoutBinding;
import com.fcn.park.property.bean.PropertyMainBean;
import com.fcn.park.property.contract.PropertyPayContract;
import com.fcn.park.property.presenter.PropertyPayPresenter;
import com.fcn.park.property.utils.customViews.PropertyBottomDialog;

/**
 * 缴费画面
 */
public class PropertyPayActivity extends BaseActivity<PropertyPayActivityLayoutBinding,
        PropertyPayContract.View, PropertyPayPresenter> implements PropertyPayContract.View {

    // 1:水费缴费 2:电费缴费 3:物业费缴费
    // 4:租赁费缴费 5:月租停车费 6:临时停车缴费
    private int mPayType;
    private String titleStr;
    private PropertyBottomDialog customDialog = null;

    @Override
    protected PropertyPayPresenter createPresenter() {

        return new PropertyPayPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 画面初始不显示内容
        PropertyMainBean bean = new PropertyMainBean();
        bean.setPayStatus(true);
        mDataBinding.setBean(bean);

        mPresenter.onDataInit(mPayType);
        Log.d("PropertyPayActivity", "onCreate");

    }

    @Override
    public void setLayoutContent(PropertyMainBean data) {
        Log.d("PropertyPayActivity", "setLayoutContent");
        data.setPayType(mPayType);
        mDataBinding.setBean(data);
        if (data.isPayStatus()) {
            mDataBinding.propertyEmptyPayment.setText(
                    String.format(getString(R.string.property_pay_empty), titleStr));
        }
    }

    @Override
    protected void setupTitle() {
        Log.d("PropertyPayActivity", "setupTitle");
        switch (mPayType) {
            case 1:
                titleStr = getString(R.string.property_water_pay_title);
                break;
            case 2:
                titleStr = getString(R.string.property_electric_pay_title);
                break;
            case 3:
                titleStr = getString(R.string.property_management_pay_title);
                break;
            case 4:
                titleStr = getString(R.string.property_rent_pay_title);
                break;
            case 5:
                titleStr = getString(R.string.property_rent_park_title);
                break;
            case 6:
                titleStr = getString(R.string.property_temporary_park_title);
                break;
            default:
                titleStr = getString(R.string.property_water_pay_title);
                break;
        }
        setTitleText(titleStr);
        // 临时停车缴费时，不显示缴费记录按钮
        if (mPayType != 6) {
            setRightMenuText(getString(R.string.property_payment_list_btn_txt));
            mTvTitleRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("缴费记录按下", "mPayType" + mPayType);
                    mPresenter.onPaymentListClick(mPayType);
                }
            });
        }
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        Log.d("PropertyPayActivity", "initViews");
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        Log.d("PropertyPayActivity", "getLayoutId");
        mPayType = getIntent().getIntExtra(Constant.PROPERTY_PAY_TYPE, 1);
        return R.layout.property_pay_activity_layout;
    }

    /**
     * 各个View的点击事件
     * @param view
     *         点击事件发生的View
     */
    public void onItemClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.property_pay_now_btn:
                Log.d("PropertyPayPresenter", "onDialogClick 点击立即缴费");
                // 打开底部Dialog
                View customDialogView = View.inflate(this, R.layout.property_custom_dialog_pay_kinds, null);
                customDialog = new PropertyBottomDialog(this, customDialogView, true, true);
                customDialog.show();
                break;

            case R.id.property_pay_icon_wei_xin:
                intent = new Intent(this, PropertyPayStatusActivity.class);
                intent.putExtra(Constant.PROPERTY_PAY_WAY, 1);
                intent.putExtra("PayNum", mDataBinding.propertyPayNum.getText());
                customDialog.dismiss();
                break;

            case R.id.property_pay_icon_alipay:
                intent = new Intent(this, PropertyPayStatusActivity.class);
                intent.putExtra(Constant.PROPERTY_PAY_WAY, 0);
                intent.putExtra("PayNum", mDataBinding.propertyPayNum.getText());
                customDialog.dismiss();
                break;

            case R.id.property_pay_icon_cancel:
                Log.d("PropertyPayPresenter", "onPayClick 点击立即缴费");
                customDialog.dismiss();
                break;
            default:
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * @return 车牌号
     */
    @Override
    public String getPlateNum() {
        return getIntent().getStringExtra(Constant.PLATE_NUMBER);
    }

    @Override
    public int getParkPayID() {
        return getIntent().getIntExtra(Constant.PROPERTY_PAY_ID, 0);
    }
}
