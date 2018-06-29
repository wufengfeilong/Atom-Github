package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.manager.activity.ElectricFeeEditActivity;
import com.fcn.park.manager.bean.ElectricFeeListBean;
import com.fcn.park.manager.contract.ElectricFeeDetailContract;

/**
 * 电费详情编辑用Presenter.
 */
public class ElectricFeeDetailPresenter
        extends BasePresenter<ElectricFeeDetailContract.View>
        implements ElectricFeeDetailContract.Presenter {

    @Override
    public void attach(ElectricFeeDetailContract.View view) {
        super.attach(view);
    }

    @Override
    public void onClickSubmit() {
        ElectricFeeListBean.ListElectricBean bean = new ElectricFeeListBean.ListElectricBean();
        // 起始电量设定
        bean.setStartNum(getView().getStartNum());
        // 截止电量设定
        bean.setEndNum(getView().getEndNum());
        // 总电量设定
        bean.setCostNum(getView().getCostNum());
        // 单价设定
        bean.setUnitPrice(getView().getUnitPrice());
        // 电费设定
        bean.setFee(getView().getElectricFee());
        // 录表日期设定
        bean.setRecordDate(getView().getRecordDate());
        // 电费ID设定
        bean.setElectricFeeId(getView().getElectricFeeId());
        ElectricFeeEditActivity.actionStart(getView().getContext(), bean);
    }
}
