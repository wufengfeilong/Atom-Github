package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.manager.activity.RentFeeEditActivity;
import com.fcn.park.manager.bean.RentFeeListBean;
import com.fcn.park.manager.contract.RentFeeDetailContract;

/**
 * 租赁费用详情画面用Presenter.
 */
public class RentFeeDetailPresenter
        extends BasePresenter<RentFeeDetailContract.View>
        implements RentFeeDetailContract.Presenter {

    @Override
    public void attach(RentFeeDetailContract.View view) {
        super.attach(view);
    }

    @Override
    public void onClickSubmit() {
        RentFeeListBean.ListRentBean bean = new RentFeeListBean.ListRentBean();
        // 租赁面积设定
        bean.setCompanySpace(getView().getCompanySpace());
        // 租赁费单价设定
        bean.setUnitPrice(getView().getRentFeeUnitPrice());
        // 开始年月设定
        bean.setStartDate(getView().getRentFeeStartDate());
        // 截止年月设定
        bean.setEndDate(getView().getRentFeeEndDate());
        // 租赁费折扣取得
        bean.setDiscount(getView().getRentFeeDiscount());
        // 租赁费设定
        bean.setFee(getView().getRentFee());
        // 备注设定
        bean.setComment(getView().getRentFeeComment());
        // 租赁费ID设定
        bean.setRentFeeId(getView().getRentFeeId());
        RentFeeEditActivity.actionStart(getView().getContext(), bean);
    }
}
