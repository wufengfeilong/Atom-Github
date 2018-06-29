package com.fcn.park.manager.presenter;

import com.fcn.park.DateSelectDialog;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.DateUtils;
import com.fcn.park.manager.activity.WaterFeeListActivity;
import com.fcn.park.manager.contract.WaterFeeEditContract;
import com.fcn.park.manager.module.WaterFeeEditModule;

/**
 * 水费明细编辑画面用Presenter.
 */
public class WaterFeeEditPresenter
        extends BasePresenter<WaterFeeEditContract.View>
        implements WaterFeeEditContract.Presenter, DateSelectDialog.OnClickListener {

    private WaterFeeEditModule mWaterFeeEditModule;
    private boolean bStartDate;

    @Override
    public void attach(WaterFeeEditContract.View view) {
        super.attach(view);
        mWaterFeeEditModule = new WaterFeeEditModule();
    }

    @Override
    public void onClick(String year, String month, boolean isNow) {
        // 日期控件的选择值
        String value = year + "-";
        if (month.length() < 2) {
            value = value + "0" + month;
        } else {
            value = value + month;
        }

        // 设置录表日期
        getView().setWaterFeeRecordDate(value);
    }

    /**
     * 录表日期点击事件
     */
    @Override
    public void onRecordDateClick() {
        bStartDate = true;
        getView().showDateSelectDialog(DateUtils.stringToDate(getView().getRecordDate()));
    }

    @Override
    public void onClickSubmit() {
        // 水费ID取得
        String waterFeeId = getView().getWaterFeeId();
        // 起始水量取得
        String startNum = getView().getStartNum();
        // 截止水量取得
        String endNum = getView().getEndNum();
        // 总水量取得
        String costNum = getView().getCostNum();
        // 水费单价取得
        String unitPrice = getView().getUnitPrice();
        // 水费取得
        String fee = getView().getWaterFee();
        // 截止日期取得
        String recordDate = getView().getRecordDate();

        // 起始水量输入check
        if (getView().checkStartNumEmpty()) {
            getView().showToast("请输入起始水量");
            return;
        } else if (getView().checkStartNumNotNum()) {
            getView().showToast("起始水量处请输入数值");
            return;
        }

        // 截止水量输入check
        if (getView().checkEndNumEmpty()) {
            getView().showToast("请输入截止水量");
            return;
        } else if (getView().checkEndNumNotNum()) {
            getView().showToast("截止水量处请输入数值");
            return;
        }

        // 水费单价输入check
        if (getView().checkUnitPriceEmpty()) {
            getView().showToast("请输入水费单价");
            return;
        } else if (getView().checkUnitPriceNotNum()) {
            getView().showToast("水费单价处请输入数值");
            return;
        }

        // 水费更新
        mWaterFeeEditModule.editWaterFee(getView().getContext(), waterFeeId, startNum, endNum, costNum, recordDate, unitPrice, fee,
                new OnDataCallback<String>() {

                    @Override
                    public void onSuccessResult(String s) {
                        getView().showToast("编辑成功");
                        getView().closeActivity();
                        WaterFeeListActivity.actionStart(getView().getContext());
                    }

                    @Override
                    public void onError(String errMsg) {
                        getView().showToast("编辑失败");
                    }
                });
    }
}