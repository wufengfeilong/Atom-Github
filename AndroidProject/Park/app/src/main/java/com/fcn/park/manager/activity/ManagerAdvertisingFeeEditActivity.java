package com.fcn.park.manager.activity;

import android.view.WindowManager;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerAdvertisingFeeEditBinding;
import com.fcn.park.manager.bean.ManagerAdvertisingFeeEditBean;
import com.fcn.park.manager.contract.ManagerAdvertisingFeeEditContract;
import com.fcn.park.manager.presenter.ManagerAdvertisingFeeEditPresenter;

import java.util.List;

/**
 * 广告费用编辑设置画面
 */
public class ManagerAdvertisingFeeEditActivity
        extends BaseActivity<ManagerAdvertisingFeeEditBinding, ManagerAdvertisingFeeEditContract.View, ManagerAdvertisingFeeEditPresenter>
        implements ManagerAdvertisingFeeEditContract.View {

    private String TAG = "=== ManagerAdvertisingFeeEditActivity ===";

    /**
     * 重写的方法，用来加载定义画面的Layout
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_advertising_fee_edit;
    }

    /**
     * 画面初始化操作：获取当前画面要显示的数据并显示到画面上
     */
    @Override
    protected void initViews() {
        // 画面初始化时，禁止软键盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mDataBinding.setAdvertisingFeeEditPresenter(mPresenter);
        mPresenter.loadAdvertisingFeeInfo();
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_advertising_position_management_fee_edit));
        openTitleLeftView(true);
    }

    @Override
    protected ManagerAdvertisingFeeEditPresenter createPresenter() {
        return new ManagerAdvertisingFeeEditPresenter();
    }

    /**
     * 将从服务器取得的广告套餐数据显示到画面上
     * @param bean
     */
    @Override
    public void showDataToView(List<ManagerAdvertisingFeeEditBean.AdvertisingFeeList> bean) {
        if (bean != null && !bean.equals("")) {
            for (int i = 0; i < bean.size(); i++) {
                if ("0".equals(bean.get(i).getAdvertiseSetId())) {// 套餐一的费用设置
                    // 显示套餐一的名字
                    mDataBinding.advertisingFeeOffer1SetRentalFeeTv.setText(bean.get(i).getAdvertiseSetName().toString().trim());
                    // 显示套餐一的费用
                    mDataBinding.advertisingFeeOffer1SetRentalFeeEt.setText(bean.get(i).getAdvertiseSetFee().toString().trim());
                    mDataBinding.advertisingFeeOffer1SetRentalFeeEt.setSelection(bean.get(i).getAdvertiseSetFee().toString().trim().length());
                }
                if ("1".equals(bean.get(i).getAdvertiseSetId())) {// 套餐二的费用设置
                    // 显示套餐一的名字
                    mDataBinding.advertisingFeeOffer2SetRentalFeeTv.setText(bean.get(i).getAdvertiseSetName().toString().trim());
                    // 显示套餐一的费用
                    mDataBinding.advertisingFeeOffer2SetRentalFeeEt.setText(bean.get(i).getAdvertiseSetFee().toString().trim());
                }
                if ("2".equals(bean.get(i).getAdvertiseSetId())) {// 套餐三的费用设置
                    // 显示套餐一的名字
                    mDataBinding.advertisingFeeOffer3SetRentalFeeTv.setText(bean.get(i).getAdvertiseSetName().toString().trim());
                    // 显示套餐一的费用
                    mDataBinding.advertisingFeeOffer3SetRentalFeeEt.setText(bean.get(i).getAdvertiseSetFee().toString().trim());
                }
            }
        }
    }

    /**
     * 获取管理员输入的套餐一（一个月）的租赁费用
     */
    @Override
    public String getInputOffer1Fee() {
        return mDataBinding.advertisingFeeOffer1SetRentalFeeEt.getText().toString().trim();
    }

    /**
     * 获取管理员输入的套餐二（三个月）的租赁费用
     */
    @Override
    public String getInputOffer2Fee() {
        return mDataBinding.advertisingFeeOffer2SetRentalFeeEt.getText().toString().trim();
    }

    /**
     * 获取管理员输入的套餐三（一年）的租赁费用
     */
    @Override
    public String getInputOffer3Fee() {
        return mDataBinding.advertisingFeeOffer3SetRentalFeeEt.getText().toString().trim();
    }
}
