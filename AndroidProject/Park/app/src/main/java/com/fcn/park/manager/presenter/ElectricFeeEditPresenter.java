package com.fcn.park.manager.presenter;

import com.fcn.park.DateSelectDialog;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.DateUtils;
import com.fcn.park.manager.activity.ElectricFeeListActivity;
import com.fcn.park.manager.contract.ElectricFeeEditContract;
import com.fcn.park.manager.module.ElectricFeeEditModule;

/**
 * Created by 860116042 on 2018/04/10.
 */

public class ElectricFeeEditPresenter extends BasePresenter<ElectricFeeEditContract.View> implements ElectricFeeEditContract.Presenter, DateSelectDialog.OnClickListener {
    private ElectricFeeEditModule mElectricFeeEditModule;
    private  boolean bStartDate;

    @Override
    public void attach(ElectricFeeEditContract.View view) {
        super.attach(view);
        mElectricFeeEditModule = new ElectricFeeEditModule();
    }

    @Override
    public void onClick(String year, String month, boolean isNow) {
        // 日期控件的选择值
        String value = year + "-";
        if(month.length() < 2){
            value = value + "0" + month;
        } else{
            value = value + month;
        }

        // 设置录表日期
        getView().setRecordDate(value);
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
        // 电费ID取得
        String electricFeeId = getView().getElectricFeeId();
        // 起始电量取得
        String startNum = getView().getStartNum();
        // 截止电量取得
        String endNum = getView().getEndNum();
        // 总电量取得
        String costNum = getView().getCostNum();
        // 电费单价取得
        String unitPrice = getView().getUnitPrice();
        // 电费取得
        String fee = getView().getElectricFee();
        // 截止日期取得
        String recordDate = getView().getRecordDate();

        // 起始电量输入check
        if (getView().checkStartNumEmpty()) {
            getView().showToast("请输入起始电量");
            return;
        } else if (getView().checkStartNumNotNum()) {
            getView().showToast("起始电量处请输入数值");
            return;
        }

        // 截止电量输入check
        if (getView().checkEndNumEmpty()) {
            getView().showToast("请输入截止电量");
            return;
        } else if (getView().checkEndNumNotNum()) {
            getView().showToast("截止电量处请输入数值");
            return;
        }

        // 电费单价输入check
        if (getView().checkUnitPriceEmpty()) {
            getView().showToast("请输入电费单价");
            return;
        } else if (getView().checkUnitPriceNotNum()) {
            getView().showToast("电费单价处请输入数值");
            return;
        }

        // 电费更新
        mElectricFeeEditModule.editElectricFee(getView().getContext(),
                electricFeeId, startNum, endNum, costNum, recordDate, unitPrice,fee,
                new OnDataCallback<String>() {

                    @Override
                    public void onSuccessResult(String s) {
                        getView().showToast("编辑成功");
                        getView().closeActivity();
                        ElectricFeeListActivity.actionStart(getView().getContext());
                    }

                    @Override
                    public void onError(String errMsg) {
                        getView().showToast("编辑失败");
                    }
                });
    }
}
