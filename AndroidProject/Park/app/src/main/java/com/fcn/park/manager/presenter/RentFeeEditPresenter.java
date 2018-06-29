package com.fcn.park.manager.presenter;

import com.fcn.park.DateSelectDialog;
import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.base.utils.DateUtils;
import com.fcn.park.manager.activity.RentFeeListActivity;
import com.fcn.park.manager.contract.RentFeeEditContract;
import com.fcn.park.manager.module.RentFeeEditModule;

/**
 * Created by 860116042 on 2018/04/10.
 */

public class RentFeeEditPresenter extends BasePresenter<RentFeeEditContract.View> implements RentFeeEditContract.Presenter, DateSelectDialog.OnClickListener {
    private RentFeeEditModule mRentFeeEditModule;
    private  boolean bStartDate;

    @Override
    public void attach(RentFeeEditContract.View view) {
        super.attach(view);
        mRentFeeEditModule = new RentFeeEditModule();
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

        if(bStartDate){
            // 设置开始日期
            getView().setRentFeeStartDate(value);
        } else {
            // 设置截止日期
            getView().setRentFeeEndDate(value);
        }
    }

    /**
     * 开始日期点击事件
     */
    @Override
    public void onStartDateClick() {
        bStartDate = true;
        getView().showDateSelectDialog(DateUtils.stringToDate(getView().getRentFeeStartDate()));
    }

    /**
     * 截止日期点击事件
     */
    @Override
    public void onEndDateClick() {
        bStartDate = false;
        getView().showDateSelectDialog(DateUtils.stringToDate(getView().getRentFeeEndDate()));
    }

    @Override
    public void onClickSubmit() {
        // 建筑面积取得
        String companySpace = getView().getCompanySpace();
        // 物业单价取得
        String propertyFeeUnitPrice = getView().getRentFeeUnitPrice();
        // 开始日期取得
        String propertyFeeStartDate = getView().getRentFeeStartDate();
        propertyFeeStartDate = propertyFeeStartDate + "-01";
        // 截止日期取得
        String propertyFeeEndDate = getView().getRentFeeEndDate();
        String[] date = propertyFeeEndDate.split("-");
        int year = Integer.valueOf(date[0]);
        int month = 1;
        if(date[1].startsWith("0")){
            month = Integer.valueOf(date[1].substring(1));
        } else{
            month = Integer.valueOf(date[1]);
        }
        int maxDay = DateUtils.getMaxDayByMonth(year, month - 1);
        propertyFeeEndDate = propertyFeeEndDate + "-" + maxDay;

        // 物业折扣取得
        String propertyFeeDiscount = getView().getRentFeeDiscount();
        // 物业费取得
        String propertyFee = getView().getRentFee();
        // 备注取得
        String propertyFeeComment = getView().getRentFeeComment();

        // 建筑面积输入check
        if (getView().checkCompanySpaceEmpty()) {
            getView().showToast("请输入租赁面积");
            return;
        } else if (getView().checkCompanySpaceNotNum()) {
            getView().showToast("租赁面积处请输入数值");
            return;
        }

        // 物业费单价输入check
        if (getView().checkUnitPriceEmpty()) {
            getView().showToast("请输入租赁费单价");
            return;
        } else if (getView().checkUnitPriceNotNum()) {
            getView().showToast("租赁费单价处请输入数值");
            return;
        }

        // 租赁费更新
        mRentFeeEditModule.editRentFee(getView().getContext(),
                "1", companySpace, propertyFeeUnitPrice, propertyFeeStartDate, propertyFeeEndDate, propertyFeeDiscount,propertyFee,propertyFeeComment,
                new OnDataCallback<String>() {

                    @Override
                    public void onSuccessResult(String s) {
                        getView().showToast("编辑成功");
                        getView().closeActivity();
                        RentFeeListActivity.actionStart(getView().getContext());
                    }

                    @Override
                    public void onError(String errMsg) {
                        getView().showToast("编辑失败");
                    }
                });
    }
}
