package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.manager.activity.WaterFeeEditActivity;
import com.fcn.park.manager.bean.WaterFeeListBean;
import com.fcn.park.manager.contract.WaterFeeDetailContract;

/**
 * Created by 860116042 on 2018/04/10.
 */

public class WaterFeeDetailPresenter extends BasePresenter<WaterFeeDetailContract.View> implements WaterFeeDetailContract.Presenter {

    @Override
    public void attach(WaterFeeDetailContract.View view) {
        super.attach(view);
    }

    @Override
    public void onClickSubmit() {
        WaterFeeListBean.ListWaterBean bean = new WaterFeeListBean.ListWaterBean();
        // 起始水量设定
        bean.setStartNum(getView().getStartNum());
        // 截止水量设定
        bean.setEndNum(getView().getEndNum());
        // 总水量设定
        bean.setCostNum(getView().getCostNum());
        // 单价设定
        bean.setUnitPrice(getView().getUnitPrice());
        // 水费设定
        bean.setFee(getView().getWaterFee());
        // 录表日期设定
        bean.setRecordDate(getView().getRecordDate());
        // 水费ID设定
        bean.setWaterFeeId(getView().getWaterFeeId());
        WaterFeeEditActivity.actionStart(getView().getContext(), bean);
    }
}
