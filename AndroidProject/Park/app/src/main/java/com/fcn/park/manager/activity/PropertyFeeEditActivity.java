package com.fcn.park.manager.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.fcn.park.DateSelectDialog;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerPropertyfeeEditBinding;
import com.fcn.park.manager.bean.PropertyFeeListBean;
import com.fcn.park.manager.contract.PropertyFeeEditContract;
import com.fcn.park.manager.presenter.PropertyFeeEditPresenter;

import java.math.BigDecimal;

/**
 * 物业费用编辑画面用Activity
 */
public class PropertyFeeEditActivity
        extends BaseActivity<ManagerPropertyfeeEditBinding, PropertyFeeEditContract.View, PropertyFeeEditPresenter>
        implements PropertyFeeEditContract.View {

    // 物业折扣
    private Spinner propertyFeeDiscount;
    // 物业费用Id
    private String propertyId = "";

    private boolean isCompanySpaceInit;
    private boolean isUnitPriceInit;
    private boolean isDiscountInit;

    private DateSelectDialog mDatePickerDialog;

    @Override
    protected PropertyFeeEditPresenter createPresenter() {
        return new PropertyFeeEditPresenter();
    }

    /**
     * @param context
     * @param bean
     */
    public static void actionStart(Context context, PropertyFeeListBean.ListPropertyBean bean) {
        Intent intent = new Intent(context, PropertyFeeEditActivity.class);
        intent.putExtra("propertyFeeId", bean.getPropertyFeeId());
        intent.putExtra("companySpace", bean.getCompanySpace());
        intent.putExtra("unitPrice", bean.getUnitPrice());
        intent.putExtra("startDate", bean.getStartDate());
        intent.putExtra("endDate", bean.getEndDate());
        intent.putExtra("discount", bean.getDiscount());
        intent.putExtra("fee", bean.getFee());
        intent.putExtra("comment", bean.getComment());
        context.startActivity(intent);
    }

    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();

        PropertyFeeListBean.ListPropertyBean bean = new PropertyFeeListBean.ListPropertyBean();

        bean.setPropertyFeeId(intent.getStringExtra("propertyFeeId"));
        propertyId = intent.getStringExtra("propertyFeeId");
        bean.setCompanySpace(intent.getStringExtra("companySpace"));
        bean.setUnitPrice(intent.getStringExtra("unitPrice"));
        bean.setStartDate(intent.getStringExtra("startDate"));
        bean.setEndDate(intent.getStringExtra("endDate"));
        String discount = intent.getStringExtra("discount");
        bean.setFee(intent.getStringExtra("fee"));
        bean.setComment(intent.getStringExtra("comment"));

        mDataBinding.managerPropertyFeeEditCompanySpace.setSelection(mDataBinding.managerPropertyFeeEditCompanySpace.getText().toString().trim().length());
        mDataBinding.setPropertyFeeEditBean(bean);

        propertyFeeDiscount = (Spinner)findViewById(R.id.manager_propertyFeeEdit_discount);
        if("无折扣".equals(discount)){
            propertyFeeDiscount.setSelection(0);
        } else if("9折".equals(discount)){
            propertyFeeDiscount.setSelection(1);
        } else if("8折".equals(discount)){
            propertyFeeDiscount.setSelection(2);
        } else {
            propertyFeeDiscount.setSelection(3);
        }
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_property_management_fee_detail_edit));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        isCompanySpaceInit = true;
        isUnitPriceInit = true;
        isDiscountInit = true;

        // 画面初始化时，禁止软键盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // 建筑面积改变时的监听事件
        mDataBinding.managerPropertyFeeEditCompanySpace.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 初期化过程中不做处理
                if(isCompanySpaceInit){
                    isCompanySpaceInit = false;
                    return;
                }

                if(mDataBinding.managerPropertyFeeEditCompanySpace.getText() == null
                        || mDataBinding.managerPropertyFeeEditUnitPrice.getText() == null
                        || propertyFeeDiscount.getSelectedItem() == null
                        || "".equals(mDataBinding.managerPropertyFeeEditCompanySpace.getText().toString().trim())
                        || "".equals(mDataBinding.managerPropertyFeeEditUnitPrice.getText().toString().trim())
                        || "".equals(propertyFeeDiscount.getSelectedItem().toString().trim())){
                    return;
                }

                // 建筑面积取得
                try{
                    BigDecimal manager_companySpace_show = new BigDecimal(mDataBinding.managerPropertyFeeEditCompanySpace.getText().toString());
                } catch (Exception e){
                    mDataBinding.getPropertyFeeEditPresenter().getView().showToast("建筑面积处请输入数值");
                }

                // 更新物业费
                setPropertyFee(propertyFeeDiscount.getSelectedItemPosition());
            }
        });

        // 物业费单价改变时的监听事件
        mDataBinding.managerPropertyFeeEditUnitPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 初期化过程中不做处理
                if(isUnitPriceInit){
                    isUnitPriceInit = false;
                    return;
                }

                if(mDataBinding.managerPropertyFeeEditCompanySpace.getText() == null
                        || mDataBinding.managerPropertyFeeEditUnitPrice.getText() == null
                        || propertyFeeDiscount.getSelectedItem() == null
                        || "".equals(mDataBinding.managerPropertyFeeEditCompanySpace.getText().toString().trim())
                        || "".equals(mDataBinding.managerPropertyFeeEditUnitPrice.getText().toString().trim())
                        || "".equals(propertyFeeDiscount.getSelectedItem().toString().trim())){
                    return;
                }

                // 物业费单价取得
                try{
                    BigDecimal manager_propertyFeeUnitPrice_show = new BigDecimal(mDataBinding.managerPropertyFeeEditUnitPrice.getText().toString());
                } catch (Exception e){
                    mDataBinding.getPropertyFeeEditPresenter().getView().showToast("物业费单价处请输入数值");
                }

                // 更新物业费
                setPropertyFee(propertyFeeDiscount.getSelectedItemPosition());
            }
        });

        // 物业费折扣改变时的监听事件
        propertyFeeDiscount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 初期化过程中不做处理
                if(isDiscountInit){
                    isDiscountInit = false;
                    return;
                }

                if(mDataBinding.managerPropertyFeeEditCompanySpace.getText() == null
                        || mDataBinding.managerPropertyFeeEditUnitPrice.getText() == null
                        || propertyFeeDiscount.getSelectedItem() == null
                        || "".equals(mDataBinding.managerPropertyFeeEditCompanySpace.getText().toString().trim())
                        || "".equals(mDataBinding.managerPropertyFeeEditUnitPrice.getText().toString().trim())
                        || "".equals(propertyFeeDiscount.getSelectedItem().toString().trim())){
                    return;
                }
                // 更新物业费
                setPropertyFee(position);
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        mDataBinding.setPropertyFeeEditPresenter(mPresenter);

        mDatePickerDialog = new DateSelectDialog(mContext).setOutnumberCurrentDate(false);
        mDatePickerDialog.setOnClickListener(mPresenter);
    }

    @Override
    protected int getLayoutId() {
       return R.layout.manager_propertyfee_edit;
    }

    // 更新物业费
    private void setPropertyFee(int position){
        try {
            // 建筑面积取得
            BigDecimal manager_companySpace_show = new BigDecimal(mDataBinding.managerPropertyFeeEditCompanySpace.getText().toString());
            // 物业单价取得
            BigDecimal manager_propertyFeeUnitPrice_show = new BigDecimal(mDataBinding.managerPropertyFeeEditUnitPrice.getText().toString());
            // 初期物业费计算 (建筑面积 × 物业单价)
            BigDecimal manager_propertyFee_show = manager_companySpace_show.multiply(manager_propertyFeeUnitPrice_show);
            // 根据物业折扣再次计算物业费
            if (position == 1) {
                manager_propertyFee_show = manager_propertyFee_show.multiply(new BigDecimal(90)).divide(new BigDecimal(100));
            } else if (position == 2) {
                manager_propertyFee_show = manager_propertyFee_show.multiply(new BigDecimal(80)).divide(new BigDecimal(100));
            } else if (position == 3) {
                manager_propertyFee_show = manager_propertyFee_show.multiply(new BigDecimal(70)).divide(new BigDecimal(100));
            }
            mDataBinding.managerPropertyFeeEditFee.setText(String.valueOf(manager_propertyFee_show));
        } catch (Exception e){
            // 出现异常时把物业费更新为空
            mDataBinding.managerPropertyFeeEditFee.setText("");
        }
    }

    @Override
    public void showDateSelectDialog(long currentTime) {
        mDatePickerDialog.setCurrentTime(currentTime);
        mDatePickerDialog.show();
    }

    @Override
    public void setPropertyFeeStartDate(String startDate) {
        mDataBinding.managerPropertyFeeEditStartDate.setText(startDate);
    }

    @Override
    public void setPropertyFeeEndDate(String startDate) {
        mDataBinding.managerPropertyFeeEditEndDate.setText(startDate);
    }

    @Override
    public String getPropertyId() {
        // 物业费Id取得
        return propertyId;
    }

    @Override
    public String getCompanySpace() {
        // 建筑面积取得
        return mDataBinding.managerPropertyFeeEditCompanySpace.getText().toString().trim();
    }

    @Override
    public String getPropertyFeeUnitPrice() {
        // 物业单价取得
        return mDataBinding.managerPropertyFeeEditUnitPrice.getText().toString().trim();
    }

    @Override
    public String getPropertyFeeStartDate() {
        // 开始日期取得
        return mDataBinding.managerPropertyFeeEditStartDate.getText().toString().trim();
    }

    @Override
    public String getPropertyFeeEndDate() {
        // 截止日期取得
        return mDataBinding.managerPropertyFeeEditEndDate.getText().toString().trim();
    }

    @Override
    public String getPropertyFeeDiscount() {
        // 物业折扣取得
        return mDataBinding.managerPropertyFeeEditDiscount.getSelectedItem().toString();
    }

    @Override
    public String getPropertyFeeComment() {
        // 备注取得
        return mDataBinding.managerPropertyFeeEditComment.getText().toString().trim();
    }

    @Override
    public String getPropertyFee(){
        // 物业费取得
        return mDataBinding.managerPropertyFeeEditFee.getText().toString().trim();
    }

    @Override
    public boolean checkCompanySpaceEmpty(){
        // 检查建筑面积是否输入
        return TextUtils.isEmpty(mDataBinding.managerPropertyFeeEditCompanySpace.getText().toString());
    }

    @Override
    public boolean checkCompanySpaceNotNum(){
        // 检查输入的建筑面积是否为数值
        try{
            BigDecimal manager_companySpace_show = new BigDecimal(mDataBinding.managerPropertyFeeEditCompanySpace.getText().toString());
            return false;
        } catch (Exception e){
            return true;
        }
    }

    @Override
    public boolean checkUnitPriceEmpty(){
        // 检查物业费单价是否输入
        return TextUtils.isEmpty(mDataBinding.managerPropertyFeeEditUnitPrice.getText().toString());
    }

    @Override
    public boolean checkUnitPriceNotNum(){
        // 检查输入的物业费单价是否为数值
        try{
            BigDecimal manager_companySpace_show = new BigDecimal(mDataBinding.managerPropertyFeeEditUnitPrice.getText().toString());
            return false;
        } catch (Exception e){
            return true;
        }
    }
}