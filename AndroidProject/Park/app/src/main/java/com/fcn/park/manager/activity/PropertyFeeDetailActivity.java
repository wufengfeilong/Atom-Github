package com.fcn.park.manager.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Spinner;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerPropertyfeeDetailBinding;
import com.fcn.park.manager.bean.PropertyFeeListBean;
import com.fcn.park.manager.contract.PropertyFeeDetailContract;
import com.fcn.park.manager.presenter.PropertyFeeDetailPresenter;

public class PropertyFeeDetailActivity
        extends BaseActivity<ManagerPropertyfeeDetailBinding, PropertyFeeDetailContract.View, PropertyFeeDetailPresenter>
        implements PropertyFeeDetailContract.View {

    // 物业折扣
    private Spinner propertyFeeComment;

    @Override
    protected PropertyFeeDetailPresenter createPresenter() {
        return new PropertyFeeDetailPresenter();
    }

    /**
     * @param context
     * @param bean
     */
    public static void actionStart(Context context, PropertyFeeListBean.ListPropertyBean bean) {
        Intent intent = new Intent(context, PropertyFeeDetailActivity.class);
        intent.putExtra("propertyFeeId", bean.getPropertyFeeId());
        intent.putExtra("companySpace", bean.getCompanySpace());
        intent.putExtra("unitPrice", bean.getUnitPrice());
        intent.putExtra("startDate", bean.getStartDate());
        intent.putExtra("endDate", bean.getEndDate());
        intent.putExtra("discount", bean.getDiscount());
        intent.putExtra("fee", bean.getFee());
        intent.putExtra("comment", bean.getComment());;
        intent.putExtra("isPay", bean.getIsPay().toString());
        context.startActivity(intent);
    }

    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();

        PropertyFeeListBean.ListPropertyBean bean = new PropertyFeeListBean.ListPropertyBean();

        bean.setPropertyFeeId(intent.getStringExtra("propertyFeeId"));
        bean.setCompanySpace(intent.getStringExtra("companySpace"));
        bean.setUnitPrice(intent.getStringExtra("unitPrice"));
        bean.setStartDate(getYm(intent.getStringExtra("startDate")));
        bean.setEndDate(getYm(intent.getStringExtra("endDate")));
        bean.setDiscount(intent.getStringExtra("discount"));
        bean.setFee(intent.getStringExtra("fee"));
        bean.setComment(intent.getStringExtra("comment"));
        bean.setIsPay(Integer.valueOf(intent.getStringExtra("isPay")));

        mDataBinding.setPropertyDetailFeeBean(bean);
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_property_management_fee_detail));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        mDataBinding.setPropertyDetailFeePresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_propertyfee_detail;
    }

    @Override
    public String getCompanySpace() {
        // 建筑面积取得
        return mDataBinding.managerPropetyFeedetailCompanySpace.getText().toString().trim();
    }

    @Override
    public String getPropertyFeeUnitPrice() {
        // 物业费单价取得
        return mDataBinding.managerPropertyFeeDetailUnitPrice.getText().toString().trim();
    }

    @Override
    public String getPropertyFeeDiscount() {
        // 物业费折扣取得
        return mDataBinding.managerPropertyFeeDetailDiscount.getText().toString();
    }

    @Override
    public String getPropertyFeeComment() {
        // 备注取得
        return mDataBinding.managerPropertyFeeDetailComment.getText().toString().trim();
    }

    @Override
    public String getPropertyFee(){
        // 物业费取得
        return mDataBinding.managerPropertyFeeDetailFee.getText().toString().trim();
    }

    @Override
    public String getPropertyFeeId(){
        // 物业费ID取得
        return mDataBinding.managerPropertyFeeDetailId.getText().toString().trim();
    }

    @Override
    public String getPropertyFeeStartDate(){
        // 开始年月取得
        return mDataBinding.managerPropertyFeeDetailStartDate.getText().toString().trim();
    }

    @Override
    public String getPropertyFeeEndDate(){
        // 截止年月取得
        return mDataBinding.managerPropertyFeeDetailEndDate.getText().toString().trim();
    }

    private String getYm(String YMDDate){
        String result = "";
        int endIndex = YMDDate.lastIndexOf("-");
        result = YMDDate.substring(0, endIndex);
        return result;
    }
}