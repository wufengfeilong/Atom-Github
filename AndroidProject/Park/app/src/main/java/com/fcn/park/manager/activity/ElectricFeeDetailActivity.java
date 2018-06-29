package com.fcn.park.manager.activity;

import android.content.Context;
import android.content.Intent;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerElectricfeeDetailBinding;
import com.fcn.park.manager.bean.ElectricFeeListBean;
import com.fcn.park.manager.contract.ElectricFeeDetailContract;
import com.fcn.park.manager.presenter.ElectricFeeDetailPresenter;

/**
 * 电费详情画面
 */
public class ElectricFeeDetailActivity
        extends BaseActivity<ManagerElectricfeeDetailBinding, ElectricFeeDetailContract.View, ElectricFeeDetailPresenter>
        implements ElectricFeeDetailContract.View {

    @Override
    protected ElectricFeeDetailPresenter createPresenter() {
        return new ElectricFeeDetailPresenter();
    }

    /**
     * @param context
     * @param bean
     */
    public static void actionStart(Context context, ElectricFeeListBean.ListElectricBean bean) {
        Intent intent = new Intent(context, ElectricFeeDetailActivity.class);
        intent.putExtra("electricFeeId", bean.getElectricFeeId());
        intent.putExtra("startNum", bean.getStartNum());
        intent.putExtra("endNum", bean.getEndNum());
        intent.putExtra("costNum", bean.getCostNum());
        intent.putExtra("unitPrice", bean.getUnitPrice());
        intent.putExtra("fee", bean.getFee());
        intent.putExtra("recordDate", bean.getRecordDate());
        intent.putExtra("isPay", bean.getIsPay().toString());
        context.startActivity(intent);
    }

    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();

        ElectricFeeListBean.ListElectricBean bean = new ElectricFeeListBean.ListElectricBean();

        bean.setElectricFeeId(intent.getStringExtra("electricFeeId"));
        bean.setStartNum(intent.getStringExtra("startNum"));
        bean.setEndNum(intent.getStringExtra("endNum"));
        bean.setCostNum((intent.getStringExtra("costNum")));
        bean.setUnitPrice((intent.getStringExtra("unitPrice")));
        bean.setFee(intent.getStringExtra("fee"));
        bean.setRecordDate(intent.getStringExtra("recordDate"));
        bean.setIsPay(Integer.valueOf(intent.getStringExtra("isPay")));

        mDataBinding.setElectricFeeDetailBean(bean);
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_electricity_fee_detail));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setElectricFeeDetailPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_electricfee_detail;
    }

    @Override
    public String getStartNum() {
        // 起始电量取得
        return mDataBinding.managerElectricFeedetailStartNum.getText().toString().trim();
    }

    @Override
    public String getEndNum() {
        // 截止电量取得
        return mDataBinding.managerElectricFeedetailEndNum.getText().toString().trim();
    }

    @Override
    public String getCostNum() {
        // 总电量取得
        return mDataBinding.managerElectricFeedetailCostNum.getText().toString();
    }

    @Override
    public String getUnitPrice() {
        // 单价取得
        return mDataBinding.managerElectricFeedetailUnitPrice.getText().toString().trim();
    }

    @Override
    public String getElectricFee() {
        // 电费取得
        return mDataBinding.managerElectricFeeDetailFee.getText().toString().trim();
    }

    @Override
    public String getRecordDate() {
        // 录表日期取得
        return mDataBinding.managerElectricFeeDetailRecordDate.getText().toString().trim();
    }

    @Override
    public String getElectricFeeId() {
        // 电费ID取得
        return mDataBinding.managerElectricFeeDetailId.getText().toString().trim();
    }
}