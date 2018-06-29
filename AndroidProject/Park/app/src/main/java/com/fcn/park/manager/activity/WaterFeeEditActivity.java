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
import com.fcn.park.databinding.ManagerWaterfeeEditBinding;
import com.fcn.park.manager.bean.WaterFeeListBean;
import com.fcn.park.manager.contract.WaterFeeEditContract;
import com.fcn.park.manager.presenter.WaterFeeEditPresenter;

import java.math.BigDecimal;

/**
 * 水费明细编辑画面
 */
public class WaterFeeEditActivity
        extends BaseActivity<ManagerWaterfeeEditBinding, WaterFeeEditContract.View, WaterFeeEditPresenter>
        implements WaterFeeEditContract.View {

    private boolean isSartNumInit;
    private boolean isEndNumInit;
    private boolean isUnitPriceInit;

    private DateSelectDialog mDatePickerDialog;

    @Override
    protected WaterFeeEditPresenter createPresenter() {
        return new WaterFeeEditPresenter();
    }

    /**
     * @param context
     * @param bean
     */
    public static void actionStart(Context context, WaterFeeListBean.ListWaterBean bean) {
        Intent intent = new Intent(context, WaterFeeEditActivity.class);
        intent.putExtra("waterFeeId", bean.getWaterFeeId());
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

        WaterFeeListBean.ListWaterBean bean = new WaterFeeListBean.ListWaterBean();

        bean.setWaterFeeId(intent.getStringExtra("waterFeeId"));
        bean.setStartNum(intent.getStringExtra("startNum"));
        bean.setEndNum(intent.getStringExtra("endNum"));
        bean.setCostNum(intent.getStringExtra("costNum"));
        bean.setUnitPrice(intent.getStringExtra("unitPrice"));
        bean.setFee(intent.getStringExtra("fee"));
        bean.setRecordDate(intent.getStringExtra("recordDate"));
        mDataBinding.managerWaterFeeEditStartNum.setSelection(mDataBinding.managerWaterFeeEditStartNum.getText().toString().trim().length());
        mDataBinding.setWaterFeeDetailEditBean(bean);
    }

    @Override
    protected void setupTitle() {
        setTitleText(getString(R.string.manager_water_fee_detail_edit));
        openTitleLeftView(true);
    }

    @Override
    protected void initViews() {
        isSartNumInit = true;
        isEndNumInit = true;
        isUnitPriceInit = true;

        // 画面初始化时，禁止软键盘弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // 起始水量改变时的监听事件
        mDataBinding.managerWaterFeeEditStartNum.addTextChangedListener(new TextWatcher() {
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

                // 起始水量或截止水量没有设值时，不更新总水量和水费
                if (mDataBinding.managerWaterFeeEditStartNum.getText() == null
                        || mDataBinding.managerWaterFeeEditEndNum.getText() == null
                        || "".equals(mDataBinding.managerWaterFeeEditStartNum.getText().toString().trim())
                        || "".equals(mDataBinding.managerWaterFeeEditEndNum.getText().toString().trim())) {
                    return;
                }

                // 起始水量取得
                try {
                    BigDecimal startNum_show = new BigDecimal(mDataBinding.managerWaterFeeEditStartNum.getText().toString().trim());
                } catch (Exception e) {
                    mDataBinding.getWaterFeeDetailEditPresenter().getView().showToast("起始水量处请输入数值");
                }

                // 更新总水量
                setCostNum();
                // 更新水费
                setFee();
            }
        });

        // 截止水量改变时的监听事件
        mDataBinding.managerWaterFeeEditEndNum.addTextChangedListener(new TextWatcher() {
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

                // 起始水量或截止水量没有设值时，不更新总水量和水费
                if (mDataBinding.managerWaterFeeEditStartNum.getText() == null
                        || mDataBinding.managerWaterFeeEditEndNum.getText() == null
                        || "".equals(mDataBinding.managerWaterFeeEditStartNum.getText().toString().trim())
                        || "".equals(mDataBinding.managerWaterFeeEditEndNum.getText().toString().trim())) {
                    return;
                }

                // 截止水量取得
                try {
                    BigDecimal endNum_show = new BigDecimal(mDataBinding.managerWaterFeeEditEndNum.getText().toString().trim());
                } catch (Exception e) {
                    mDataBinding.getWaterFeeDetailEditPresenter().getView().showToast("截止水量处请输入数值");
                }

                // 更新总水量
                setCostNum();
                // 更新水费
                setFee();
            }
        });

        // 水费单价改变时的监听事件
        mDataBinding.managerWaterFeeEditUnitPrice.addTextChangedListener(new TextWatcher() {
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

                // 总水量或水费单价没设值时不做处理
                if (mDataBinding.managerWaterFeeEditCostNum == null ||
                        "".equals(mDataBinding.managerWaterFeeEditCostNum.getText().toString().trim()) ||
                        mDataBinding.managerWaterFeeEditUnitPrice == null ||
                        "".equals(mDataBinding.managerWaterFeeEditUnitPrice.getText().toString().trim())) {
                    return;
                }

                // 水费单价取得
                try {
                    BigDecimal unitPrice_show = new BigDecimal(mDataBinding.managerWaterFeeEditUnitPrice.getText().toString());
                } catch (Exception e) {
                    mDataBinding.getWaterFeeDetailEditPresenter().getView().showToast("水费单价处请输入数值");
                }

                // 更新水费
                setFee();
            }
        });

        mDataBinding.setWaterFeeDetailEditPresenter(mPresenter);

        mDatePickerDialog = new DateSelectDialog(mContext).setOutnumberCurrentDate(false);
        mDatePickerDialog.setOnClickListener(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.manager_waterfee_edit;
    }

    // 更新总水量
    private void setCostNum() {
        try {
            BigDecimal startNumDecimal = new BigDecimal(mDataBinding.managerWaterFeeEditStartNum.getText().toString());
            BigDecimal endNumDecimal = new BigDecimal(mDataBinding.managerWaterFeeEditEndNum.getText().toString());
            if (startNumDecimal.compareTo(endNumDecimal) > 0) {
                return;
            } else {
                BigDecimal costNumDecimal = endNumDecimal.subtract(startNumDecimal);
                // 更新总水量
                mDataBinding.managerWaterFeeEditCostNum.setText(costNumDecimal.toString());
            }
        } catch (Exception ex) {
            mDataBinding.managerWaterFeeEditCostNum.setText("");
        }
    }

    // 更新水费
    private void setFee() {
        try {
            // 总水量或水费单价没设值时不做处理
            if (mDataBinding.managerWaterFeeEditCostNum == null ||
                    "".equals(mDataBinding.managerWaterFeeEditCostNum.getText().toString().trim()) ||
                    mDataBinding.managerWaterFeeEditUnitPrice == null ||
                    "".equals(mDataBinding.managerWaterFeeEditUnitPrice.getText().toString().trim())) {
                return;
            }

            BigDecimal costNumDecimal = new BigDecimal(mDataBinding.managerWaterFeeEditCostNum.getText().toString().trim());
            BigDecimal unitPrice = new BigDecimal(mDataBinding.managerWaterFeeEditUnitPrice.getText().toString().trim());
            BigDecimal feeDecimal = costNumDecimal.multiply(unitPrice);
            mDataBinding.managerWaterFeeEditFee.setText(feeDecimal.toString());
        } catch (Exception ex) {
            mDataBinding.managerWaterFeeEditFee.setText("");
        }
    }

    @Override
    public void showDateSelectDialog(long currentTime) {
        mDatePickerDialog.setCurrentTime(currentTime);
        mDatePickerDialog.show();
    }

    @Override
    public String getStartNum() {
        // 起始水量取得
        return mDataBinding.managerWaterFeeEditStartNum.getText().toString().trim();
    }

    @Override
    public String getEndNum() {
        // 截止水量取得
        return mDataBinding.managerWaterFeeEditEndNum.getText().toString().trim();
    }

    @Override
    public String getCostNum() {
        // 总水量取得
        return mDataBinding.managerWaterFeeEditCostNum.getText().toString();
    }

    @Override
    public String getUnitPrice() {
        // 单价取得
        return mDataBinding.managerWaterFeeEditUnitPrice.getText().toString().trim();
    }

    @Override
    public String getWaterFee() {
        // 水费取得
        return mDataBinding.managerWaterFeeEditFee.getText().toString().trim();
    }

    @Override
    public String getRecordDate() {
        // 录表日期取得
        return mDataBinding.managerWaterFeeEditRecordDate.getText().toString().trim();
    }

    @Override
    public void setWaterFeeRecordDate(String recordDate) {
        // 设置截止日期
        mDataBinding.managerWaterFeeEditRecordDate.setText(recordDate);
    }

    ;

    @Override
    public String getWaterFeeId() {
        // 水费ID取得
        return mDataBinding.managerWaterFeeEditId.getText().toString().trim();
    }

    @Override
    public boolean checkStartNumEmpty() {
        // 检查起始水量是否输入
        return TextUtils.isEmpty(mDataBinding.managerWaterFeeEditStartNum.getText().toString());
    }

    @Override
    public boolean checkStartNumNotNum() {
        // 检查输入的起始水量是否为数值
        try {
            BigDecimal startNum = new BigDecimal(mDataBinding.managerWaterFeeEditStartNum.getText().toString());
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public boolean checkEndNumEmpty() {
        // 检查截止水量是否输入
        return TextUtils.isEmpty(mDataBinding.managerWaterFeeEditEndNum.getText().toString());
    }

    @Override
    public boolean checkEndNumNotNum() {
        // 检查输入的截止水量是否为数值
        try {
            BigDecimal startNum = new BigDecimal(mDataBinding.managerWaterFeeEditEndNum.getText().toString());
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public boolean checkUnitPriceEmpty() {
        // 检查水费单价是否输入
        return TextUtils.isEmpty(mDataBinding.managerWaterFeeEditUnitPrice.getText().toString());
    }

    @Override
    public boolean checkUnitPriceNotNum() {
        // 检查输入的水费单价是否为数值
        try {
            BigDecimal unitPrice = new BigDecimal(mDataBinding.managerWaterFeeEditUnitPrice.getText().toString());
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
