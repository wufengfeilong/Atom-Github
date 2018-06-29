package com.fcn.park.manager.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Spinner;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerWaterfeeDetailBinding;
import com.fcn.park.manager.bean.WaterFeeListBean;
import com.fcn.park.manager.contract.WaterFeeDetailContract;
import com.fcn.park.manager.presenter.WaterFeeDetailPresenter;

/**
 * 水费详情画面
 */
public class WaterFeeDetailActivity
        extends BaseActivity<ManagerWaterfeeDetailBinding, WaterFeeDetailContract.View, WaterFeeDetailPresenter>
        implements WaterFeeDetailContract.View {

    @Override
    protected WaterFeeDetailPresenter createPresenter() {
        return new WaterFeeDetailPresenter();
    }

    /**
     * @param context
     * @param bean
     */
    public static void actionStart(Context context, WaterFeeListBean.ListWaterBean bean) {
        Intent intent = new Intent(context, WaterFeeDetailActivity.class);
        intent.putExtra("waterFeeId", bean.getWaterFeeId());
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

        WaterFeeListBean.ListWaterBean bean = new WaterFeeListBean.ListWaterBean();

        bean.setWaterFeeId(intent.getStringExtra("waterFeeId"));
        bean.setStartNum(intent.getStringExtra("startNum"));
        bean.setEndNum(intent.getStringExtra("endNum"));
        bean.setCostNum((intent.getStringExtra("costNum")));
        bean.setUnitPrice((intent.getStringExtra("unitPrice")));
        bean.setFee(intent.getStringExtra("fee"));
        bean.setRecordDate(intent.getStringExtra("recordDate"));
        bean.setIsPay(Integer.valueOf(intent.getStringExtra("isPay")));

        mDataBinding.setWaterFeeDetailBean(bean);
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_water_fee_detail));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setWaterFeeDetailPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_waterfee_detail;
    }

    @Override
    public String getStartNum() {
        // 起始水量取得
        return mDataBinding.managerWaterFeedetailStartNum.getText().toString().trim();
    }

    @Override
    public String getEndNum() {
        // 截止水量取得
        return mDataBinding.managerWaterFeedetailEndNum.getText().toString().trim();
    }

    @Override
    public String getCostNum() {
        // 总水量取得
        return mDataBinding.managerWaterFeedetailCostNum.getText().toString();
    }

    @Override
    public String getUnitPrice() {
        // 单价取得
        return mDataBinding.managerWaterFeedetailUnitPrice.getText().toString().trim();
    }

    @Override
    public String getWaterFee() {
        // 水费取得
        return mDataBinding.managerWaterFeeDetailFee.getText().toString().trim();
    }

    @Override
    public String getRecordDate() {
        // 录表日期取得
        return mDataBinding.managerWaterFeeDetailRecordDate.getText().toString().trim();
    }

    @Override
    public String getWaterFeeId() {
        // 水费ID取得
        return mDataBinding.managerWaterFeeDetailId.getText().toString().trim();
    }
}
