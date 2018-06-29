package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.manager.bean.PropertyFeeListBean;
import com.fcn.park.manager.contract.PropertyFeeDetailContract;
import com.fcn.park.manager.activity.PropertyFeeEditActivity;

/**
 * 物业费用明细编辑画面.
 */
public class PropertyFeeDetailPresenter
        extends BasePresenter<PropertyFeeDetailContract.View>
        implements PropertyFeeDetailContract.Presenter {

    @Override
    public void attach(PropertyFeeDetailContract.View view) {
        super.attach(view);
    }

    @Override
    public void onClickEdit() {
        PropertyFeeListBean.ListPropertyBean bean = new PropertyFeeListBean.ListPropertyBean();
        // 建筑面积设定
        bean.setCompanySpace(getView().getCompanySpace());
        // 物业费单价设定
        bean.setUnitPrice(getView().getPropertyFeeUnitPrice());
        // 开始年月设定
        bean.setStartDate(getView().getPropertyFeeStartDate());
        // 截止年月设定
        bean.setEndDate(getView().getPropertyFeeEndDate());
        // 物业费折扣取得
        bean.setDiscount(getView().getPropertyFeeDiscount());
        // 物业费设定
        bean.setFee(getView().getPropertyFee());
        // 备注设定
        bean.setComment(getView().getPropertyFeeComment());
        // 物业费ID设定
        bean.setPropertyFeeId(getView().getPropertyFeeId());
        PropertyFeeEditActivity.actionStart(getView().getContext(), bean);
    }
}
