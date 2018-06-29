package com.fcn.park.manager.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.WindowManager;

import com.fcn.park.DateSelectDialog;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ManagerElectricfeeEditBinding;
import com.fcn.park.manager.bean.ElectricFeeListBean;
import com.fcn.park.manager.contract.ElectricFeeEditContract;
import com.fcn.park.manager.presenter.ElectricFeeEditPresenter;

import java.math.BigDecimal;

/**
 * 电费详情编辑画面
 */
public class ElectricFeeEditActivity
        extends BaseActivity<ManagerElectricfeeEditBinding, ElectricFeeEditContract.View, ElectricFeeEditPresenter>
        implements ElectricFeeEditContract.View {

    private boolean isSartNumInit;
    private boolean isEndNumInit;
    private boolean isUnitPriceInit;

    private DateSelectDialog mDatePickerDialog;

    @Override
    protected ElectricFeeEditPresenter createPresenter() {
        return new ElectricFeeEditPresenter();
    }

    /**
     * @param context
     * @param bean
     */
    public static void actionStart(Context context, ElectricFeeListBean.ListElectricBean bean) {
        Intent intent = new Intent(context, ElectricFeeEditActivity.class);
        intent.putExtra("electricFeeId", bean.getElectricFeeId());
        intent.putExtra("startNum", bean.getStartNum());
        intent.putExtra("endNum", bean.getEndNum());
        intent.putExtra("costNum", bean.getCostNum());
        intent.putExtra("unitPrice", bean.getUnitPrice());
        intent.putExtra("fee", bean.getFee());
        intent.putExtra("recordDate", bean.getRecordDate());
        context.startActivity(intent);
    }

    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();

        ElectricFeeListBean.ListElectricBean bean = new ElectricFeeListBean.ListElectricBean();

        bean.setElectricFeeId(intent.getStringExtra("electricrFeeId"));
        bean.setStartNum(intent.getStringExtra("startNum"));
        bean.setEndNum(intent.getStringExtra("endNum"));
        bean.setCostNum(intent.getStringExtra("costNum"));
        bean.setUnitPrice(intent.getStringExtra("unitPrice"));
        bean.setFee(intent.getStringExtra("fee"));
        bean.setRecordDate(intent.getStringExtra("recordDate"));

        mDataBinding.setElectricFeeDetailEditBean(bean);
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_electricity_fee_detail_edit));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        isSartNumInit = true;
        isEndNumInit = true;
        isUnitPriceInit = true;

        // 画面初始化时，禁止软键盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // 起始电量改变时的监听事件
        mDataBinding.managerElectricFeeEditStartNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 初期化过程中不做处理
                if (isSartNumInit) {
                    isSartNumInit = false;
                    return;
                }

                // 起始电量或截止电量没有设值时，不更新总电量和电费
                if (mDataBinding.managerElectricFeeEditStartNum.getText() == null
                        || mDataBinding.managerElectricFeeEditEndNum.getText() == null
                        || "".equals(mDataBinding.managerElectricFeeEditStartNum.getText().toString().trim())
                        || "".equals(mDataBinding.managerElectricFeeEditEndNum.getText().toString().trim())) {
                    return;
                }

                // 起始电量取得
                try {
                    BigDecimal startNum_show = new BigDecimal(mDataBinding.managerElectricFeeEditStartNum.getText().toString().trim());
                } catch (Exception e) {
                    mDataBinding.getElectricFeeDetailEditPresenter().getView().showToast("起始电量处请输入数值");
                }

                // 更新总电量
                setCostNum();
                // 更新电费
                setFee();
            }
        });

        // 截止电量改变时的监听事件
        mDataBinding.managerElectricFeeEditEndNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 初期化过程中不做处理
                if (isEndNumInit) {
                    isEndNumInit = false;
                    return;
                }

                // 起始电量或截止电量没有设值时，不更新总电量和电费
                if (mDataBinding.managerElectricFeeEditStartNum.getText() == null
                        || mDataBinding.managerElectricFeeEditEndNum.getText() == null
                        || "".equals(mDataBinding.managerElectricFeeEditStartNum.getText().toString().trim())
                        || "".equals(mDataBinding.managerElectricFeeEditEndNum.getText().toString().trim())) {
                    return;
                }

                // 截止电量取得
                try {
                    BigDecimal endNum_show = new BigDecimal(mDataBinding.managerElectricFeeEditEndNum.getText().toString().trim());
                } catch (Exception e) {
                    mDataBinding.getElectricFeeDetailEditPresenter().getView().showToast("截止电量处请输入数值");
                }

                // 更新总电量
                setCostNum();
                // 更新电费
                setFee();
            }
        });

        // 电费单价改变时的监听事件
        mDataBinding.managerElectricFeeEditUnitPrice.addTextChangedListener(new TextWatcher() {
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

                // 总电量或电费单价没设值时不做处理
                if (mDataBinding.managerElectricFeeEditCostNum == null ||
                        "".equals(mDataBinding.managerElectricFeeEditCostNum.getText().toString().trim()) ||
                        mDataBinding.managerElectricFeeEditUnitPrice == null ||
                        "".equals(mDataBinding.managerElectricFeeEditUnitPrice.getText().toString().trim())) {
                    return;
                }

                // 电费单价取得
                try {
                    BigDecimal unitPrice_show = new BigDecimal(mDataBinding.managerElectricFeeEditUnitPrice.getText().toString());
                } catch (Exception e) {
                    mDataBinding.getElectricFeeDetailEditPresenter().getView().showToast("电费单价处请输入数值");
                }

                // 更新电费
                setFee();
            }
        });

        mDataBinding.setElectricFeeDetailEditPresenter(mPresenter);

        mDatePickerDialog = new DateSelectDialog(mContext).setOutnumberCurrentDate(false);
        mDatePickerDialog.setOnClickListener(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_electricfee_edit;
    }

    // 更新总电量
    private void setCostNum() {
        try {
            BigDecimal startNumDecimal = new BigDecimal(mDataBinding.managerElectricFeeEditStartNum.getText().toString());
            BigDecimal endNumDecimal = new BigDecimal(mDataBinding.managerElectricFeeEditEndNum.getText().toString());
            if (startNumDecimal.compareTo(endNumDecimal) > 0) {
                return;
            } else {
                BigDecimal costNumDecimal = endNumDecimal.subtract(startNumDecimal);
                // 更新总电量
                mDataBinding.managerElectricFeeEditCostNum.setText(costNumDecimal.toString());
            }
        } catch (Exception ex) {
            mDataBinding.managerElectricFeeEditCostNum.setText("");
        }
    }

    // 更新电费
    private void setFee() {
        try {
            // 总电量或电费单价没设值时不做处理
            if (mDataBinding.managerElectricFeeEditCostNum == null ||
                    "".equals(mDataBinding.managerElectricFeeEditCostNum.getText().toString().trim()) ||
                    mDataBinding.managerElectricFeeEditUnitPrice == null ||
                    "".equals(mDataBinding.managerElectricFeeEditUnitPrice.getText().toString().trim())) {
                return;
            }

            BigDecimal costNumDecimal = new BigDecimal(mDataBinding.managerElectricFeeEditCostNum.getText().toString().trim());
            BigDecimal unitPrice = new BigDecimal(mDataBinding.managerElectricFeeEditUnitPrice.getText().toString().trim());
            BigDecimal feeDecimal = costNumDecimal.multiply(unitPrice);
            mDataBinding.managerElectricFeeEditFee.setText(feeDecimal.toString());
        } catch (Exception ex) {
            mDataBinding.managerElectricFeeEditFee.setText("");
        }
    }

    @Override
    public void showDateSelectDialog(long currentTime) {
        mDatePickerDialog.setCurrentTime(currentTime);
        mDatePickerDialog.show();
    }

    @Override
    public String getStartNum() {
        // 起始电量取得
        return mDataBinding.managerElectricFeeEditStartNum.getText().toString().trim();
    }

    @Override
    public String getEndNum() {
        // 截止电量取得
        return mDataBinding.managerElectricFeeEditEndNum.getText().toString().trim();
    }

    @Override
    public String getCostNum() {
        // 总电量取得
        return mDataBinding.managerElectricFeeEditCostNum.getText().toString();
    }

    @Override
    public String getUnitPrice() {
        // 单价取得
        return mDataBinding.managerElectricFeeEditUnitPrice.getText().toString().trim();
    }

    @Override
    public String getElectricFee() {
        // 电费取得
        return mDataBinding.managerElectricFeeEditFee.getText().toString().trim();
    }

    @Override
    public String getRecordDate() {
        // 录表日期取得
        return mDataBinding.managerElectricFeeEditRecordDate.getText().toString().trim();
    }

    @Override
    public void setRecordDate(String recordDate) {
        // 设置截止日期
        mDataBinding.managerElectricFeeEditRecordDate.setText(recordDate);
    }

    ;

    @Override
    public String getElectricFeeId() {
        // 电费ID取得
        return mDataBinding.managerElectricFeeEditId.getText().toString().trim();
    }

    @Override
    public boolean checkStartNumEmpty() {
        // 检查起始电量是否输入
        return TextUtils.isEmpty(mDataBinding.managerElectricFeeEditStartNum.getText().toString());
    }

    @Override
    public boolean checkStartNumNotNum() {
        // 检查输入的起始电量是否为数值
        try {
            BigDecimal startNum = new BigDecimal(mDataBinding.managerElectricFeeEditStartNum.getText().toString());
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public boolean checkEndNumEmpty() {
        // 检查截止电量是否输入
        return TextUtils.isEmpty(mDataBinding.managerElectricFeeEditEndNum.getText().toString());
    }

    @Override
    public boolean checkEndNumNotNum() {
        // 检查输入的截止电量是否为数值
        try {
            BigDecimal startNum = new BigDecimal(mDataBinding.managerElectricFeeEditEndNum.getText().toString());
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public boolean checkUnitPriceEmpty() {
        // 检查电费单价是否输入
        return TextUtils.isEmpty(mDataBinding.managerElectricFeeEditUnitPrice.getText().toString());
    }

    @Override
    public boolean checkUnitPriceNotNum() {
        // 检查输入的电费单价是否为数值
        try {
            BigDecimal unitPrice = new BigDecimal(mDataBinding.managerElectricFeeEditUnitPrice.getText().toString());
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}