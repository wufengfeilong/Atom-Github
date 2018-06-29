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
import com.fcn.park.databinding.ManagerRentfeeEditBinding;
import com.fcn.park.manager.bean.RentFeeListBean;
import com.fcn.park.manager.contract.RentFeeEditContract;
import com.fcn.park.manager.presenter.RentFeeEditPresenter;

import java.math.BigDecimal;

public class RentFeeEditActivity
        extends BaseActivity<ManagerRentfeeEditBinding, RentFeeEditContract.View, RentFeeEditPresenter>
        implements RentFeeEditContract.View {

    // 租赁费折扣
    private Spinner rentFeeDiscount;

    private boolean isCompanySpaceInit;
    private boolean isUnitPriceInit;
    private boolean isDiscountInit;

    private DateSelectDialog mDatePickerDialog;

    @Override
    protected RentFeeEditPresenter createPresenter() {
        return new RentFeeEditPresenter();
    }

    /**
     * @param context
     * @param bean
     */
    public static void actionStart(Context context, RentFeeListBean.ListRentBean bean) {
        Intent intent = new Intent(context, RentFeeEditActivity.class);
        intent.putExtra("rentFeeId", bean.getRentFeeId());
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

        RentFeeListBean.ListRentBean bean = new RentFeeListBean.ListRentBean();

        bean.setRentFeeId(intent.getStringExtra("rentFeeId"));
        bean.setCompanySpace(intent.getStringExtra("companySpace"));
        bean.setUnitPrice(intent.getStringExtra("unitPrice"));
        bean.setStartDate(intent.getStringExtra("startDate"));
        bean.setEndDate(intent.getStringExtra("endDate"));
        String discount = intent.getStringExtra("discount");
        bean.setFee(intent.getStringExtra("fee"));
        bean.setComment(intent.getStringExtra("comment"));

        mDataBinding.managerRentFeeEditCompanySpace.setSelection(mDataBinding.managerRentFeeEditCompanySpace.getText().toString().trim().length());
        mDataBinding.setRentFeeEditBean(bean);

        rentFeeDiscount = (Spinner) findViewById(R.id.manager_rentFeeEdit_discount);
        if ("无折扣".equals(discount)) {
            rentFeeDiscount.setSelection(0);
        } else if ("9折".equals(discount)) {
            rentFeeDiscount.setSelection(1);
        } else if ("8折".equals(discount)) {
            rentFeeDiscount.setSelection(2);
        } else {
            rentFeeDiscount.setSelection(3);
        }
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_rent_detail));
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
        mDataBinding.managerRentFeeEditCompanySpace.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 初期化过程中不做处理
                if (isCompanySpaceInit) {
                    isCompanySpaceInit = false;
                    return;
                }

                if (mDataBinding.managerRentFeeEditCompanySpace.getText() == null
                        || mDataBinding.managerRentFeeEditUnitPrice.getText() == null
                        || rentFeeDiscount.getSelectedItem() == null
                        || "".equals(mDataBinding.managerRentFeeEditCompanySpace.getText().toString().trim())
                        || "".equals(mDataBinding.managerRentFeeEditUnitPrice.getText().toString().trim())
                        || "".equals(rentFeeDiscount.getSelectedItem().toString().trim())) {
                    return;
                }

                // 建筑面积取得
                try {
                    BigDecimal manager_companySpace_show = new BigDecimal(mDataBinding.managerRentFeeEditCompanySpace.getText().toString());
                } catch (Exception e) {
                    mDataBinding.getRentFeeEditPresenter().getView().showToast("租赁面积处请输入数值");
                }

                // 更新租赁费
                setRentFee(rentFeeDiscount.getSelectedItemPosition());
            }
        });

        // 租赁费单价改变时的监听事件
        mDataBinding.managerRentFeeEditUnitPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 初期化过程中不做处理
                if (isUnitPriceInit) {
                    isUnitPriceInit = false;
                    return;
                }

                if (mDataBinding.managerRentFeeEditCompanySpace.getText() == null
                        || mDataBinding.managerRentFeeEditUnitPrice.getText() == null
                        || rentFeeDiscount.getSelectedItem() == null
                        || "".equals(mDataBinding.managerRentFeeEditCompanySpace.getText().toString().trim())
                        || "".equals(mDataBinding.managerRentFeeEditUnitPrice.getText().toString().trim())
                        || "".equals(rentFeeDiscount.getSelectedItem().toString().trim())) {
                    return;
                }

                // 租赁费单价取得
                try {
                    BigDecimal manager_rentFeeUnitPrice_show = new BigDecimal(mDataBinding.managerRentFeeEditUnitPrice.getText().toString());
                } catch (Exception e) {
                    mDataBinding.getRentFeeEditPresenter().getView().showToast("租赁费单价处请输入数值");
                }

                // 更新物业费
                setRentFee(rentFeeDiscount.getSelectedItemPosition());
            }
        });

        // 物业费折扣改变时的监听事件
        rentFeeDiscount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 初期化过程中不做处理
                if (isDiscountInit) {
                    isDiscountInit = false;
                    return;
                }

                if (mDataBinding.managerRentFeeEditCompanySpace.getText() == null
                        || mDataBinding.managerRentFeeEditUnitPrice.getText() == null
                        || rentFeeDiscount.getSelectedItem() == null
                        || "".equals(mDataBinding.managerRentFeeEditCompanySpace.getText().toString().trim())
                        || "".equals(mDataBinding.managerRentFeeEditUnitPrice.getText().toString().trim())
                        || "".equals(rentFeeDiscount.getSelectedItem().toString().trim())) {
                    return;
                }
                // 更新租赁费
                setRentFee(position);
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        mDataBinding.setRentFeeEditPresenter(mPresenter);

        mDatePickerDialog = new DateSelectDialog(mContext).setOutnumberCurrentDate(false);
        mDatePickerDialog.setOnClickListener(mPresenter);
        mDataBinding.managerRentFeeEditCompanySpace.setSelection(mDataBinding.managerRentFeeEditCompanySpace.getText().toString().trim().length());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_rentfee_edit;
    }

    // 更新物业费
    private void setRentFee(int position) {
        try {
            // 租赁面积取得
            BigDecimal manager_companySpace_show = new BigDecimal(mDataBinding.managerRentFeeEditCompanySpace.getText().toString());
            // 租赁费单价取得
            BigDecimal manager_rentFeeUnitPrice_show = new BigDecimal(mDataBinding.managerRentFeeEditUnitPrice.getText().toString());
            // 初期租赁费计算 (租赁面积 × 物业单价)
            BigDecimal manager_rentFee_show = manager_companySpace_show.multiply(manager_rentFeeUnitPrice_show);
            // 根据租赁费折扣再次计算租赁费
            if (position == 1) {
                manager_rentFee_show = manager_rentFee_show.multiply(new BigDecimal(90)).divide(new BigDecimal(100));
            } else if (position == 2) {
                manager_rentFee_show = manager_rentFee_show.multiply(new BigDecimal(80)).divide(new BigDecimal(100));
            } else if (position == 3) {
                manager_rentFee_show = manager_rentFee_show.multiply(new BigDecimal(70)).divide(new BigDecimal(100));
            }
            mDataBinding.managerRentFeeEditFee.setText(String.valueOf(manager_rentFee_show));
        } catch (Exception e) {
            // 出现异常时把物业费更新为空
            mDataBinding.managerRentFeeEditFee.setText("");
        }
    }

    @Override
    public void showDateSelectDialog(long currentTime) {
        mDatePickerDialog.setCurrentTime(currentTime);
        mDatePickerDialog.show();
    }

    @Override
    public void setRentFeeStartDate(String startDate) {
        mDataBinding.managerRentFeeEditStartDate.setText(startDate);
    }

    @Override
    public void setRentFeeEndDate(String startDate) {
        mDataBinding.managerRentFeeEditEndDate.setText(startDate);
    }

    @Override
    public String getCompanySpace() {
        // 租赁面积取得
        return mDataBinding.managerRentFeeEditCompanySpace.getText().toString().trim();
    }

    @Override
    public String getRentFeeUnitPrice() {
        // 租赁费单价取得
        return mDataBinding.managerRentFeeEditUnitPrice.getText().toString().trim();
    }

    @Override
    public String getRentFeeStartDate() {
        // 开始日期取得
        return mDataBinding.managerRentFeeEditStartDate.getText().toString().trim();
    }

    @Override
    public String getRentFeeEndDate() {
        // 截止日期取得
        return mDataBinding.managerRentFeeEditEndDate.getText().toString().trim();
    }

    @Override
    public String getRentFeeDiscount() {
        // 租赁费折扣取得
        return mDataBinding.managerRentFeeEditDiscount.getSelectedItem().toString();
    }

    @Override
    public String getRentFeeComment() {
        // 备注取得
        return mDataBinding.managerRentFeeEditComment.getText().toString().trim();
    }

    @Override
    public String getRentFee() {
        // 租赁费取得
        return mDataBinding.managerRentFeeEditFee.getText().toString().trim();
    }

    @Override
    public boolean checkCompanySpaceEmpty() {
        // 检查租赁面积是否输入
        return TextUtils.isEmpty(mDataBinding.managerRentFeeEditCompanySpace.getText().toString());
    }

    @Override
    public boolean checkCompanySpaceNotNum() {
        // 检查输入的租赁面积是否为数值
        try {
            BigDecimal manager_companySpace_show = new BigDecimal(mDataBinding.managerRentFeeEditCompanySpace.getText().toString());
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public boolean checkUnitPriceEmpty() {
        // 检查租赁费单价是否输入
        return TextUtils.isEmpty(mDataBinding.managerRentFeeEditUnitPrice.getText().toString());
    }

    @Override
    public boolean checkUnitPriceNotNum() {
        // 检查输入的租赁费单价是否为数值
        try {
            BigDecimal manager_companySpace_show = new BigDecimal(mDataBinding.managerRentFeeEditUnitPrice.getText().toString());
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
