package com.fcn.park.manager.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Spinner;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerRentfeeDetailBinding;
import com.fcn.park.manager.bean.RentFeeListBean;
import com.fcn.park.manager.contract.RentFeeDetailContract;
import com.fcn.park.manager.presenter.RentFeeDetailPresenter;

/**
 * 租赁费用详情画面用Activity
 */
public class RentFeeDetailActivity
        extends BaseActivity<ManagerRentfeeDetailBinding, RentFeeDetailContract.View, RentFeeDetailPresenter>
        implements RentFeeDetailContract.View {

    // 租赁费折扣
    private Spinner rentFeeComment;

    @Override
    protected RentFeeDetailPresenter createPresenter() {
        return new RentFeeDetailPresenter();
    }

    /**
     * @param context
     * @param bean
     */
    public static void actionStart(Context context, RentFeeListBean.ListRentBean bean) {
        Intent intent = new Intent(context, RentFeeDetailActivity.class);
        intent.putExtra("rentFeeId", bean.getRentFeeId());
        intent.putExtra("companySpace", bean.getCompanySpace());
        intent.putExtra("unitPrice", bean.getUnitPrice());
        intent.putExtra("startDate", bean.getStartDate());
        intent.putExtra("endDate", bean.getEndDate());
        intent.putExtra("discount", bean.getDiscount());
        intent.putExtra("fee", bean.getFee());
        intent.putExtra("comment", bean.getComment());
        ;
        intent.putExtra("isPay", bean.getIsPay().toString());
        context.startActivity(intent);
    }

    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();

        RentFeeListBean.ListRentBean bean = new RentFeeListBean.ListRentBean();

        bean.setRentFeeId(intent.getStringExtra("rentFeeId"));
        bean.setCompanySpace(intent.getStringExtra("companySpace"));
        bean.setUnitPrice(intent.getStringExtra("unitPrice"));
        bean.setStartDate(getYm(intent.getStringExtra("startDate")));
        bean.setEndDate(getYm(intent.getStringExtra("endDate")));
        bean.setDiscount(intent.getStringExtra("discount"));
        bean.setFee(intent.getStringExtra("fee"));
        bean.setComment(intent.getStringExtra("comment"));
        bean.setIsPay(Integer.valueOf(intent.getStringExtra("isPay")));

        mDataBinding.setRentFeeDetailBean(bean);
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_rent_detail));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setRentFeeDetailPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_rentfee_detail;
    }

    @Override
    public String getCompanySpace() {
        // 租赁面积取得
        return mDataBinding.managerRentFeedetailCompanySpace.getText().toString().trim();
    }

    @Override
    public String getRentFeeUnitPrice() {
        // 租赁费单价取得
        return mDataBinding.managerRentFeeDetailUnitPrice.getText().toString().trim();
    }

    @Override
    public String getRentFeeDiscount() {
        // 租赁费折扣取得
        return mDataBinding.managerRentFeeDetailDiscount.getText().toString();
    }

    @Override
    public String getRentFeeComment() {
        // 备注取得
        return mDataBinding.managerRentFeeDetailComment.getText().toString().trim();
    }

    @Override
    public String getRentFee() {
        // 租赁费取得
        return mDataBinding.managerRentFeeDetailFee.getText().toString().trim();
    }

    @Override
    public String getRentFeeId() {
        // 租赁费ID取得
        return mDataBinding.managerRentFeeDetailId.getText().toString().trim();
    }

    @Override
    public String getRentFeeStartDate() {
        // 开始年月取得
        return mDataBinding.managerRentFeeDetailStartDate.getText().toString().trim();
    }

    @Override
    public String getRentFeeEndDate() {
        // 截止年月取得
        return mDataBinding.managerRentFeeDetailEndDate.getText().toString().trim();
    }

    private String getYm(String YMDDate) {
        String result = "";
        int endIndex = YMDDate.lastIndexOf("-");
        result = YMDDate.substring(0, endIndex);
        return result;
    }
}