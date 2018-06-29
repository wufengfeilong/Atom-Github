package com.fcn.park.property.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.property.contract.PropertyPaymentDetailContract;

/**
 * 绿色物管缴费记录详情的Presenter
 */

public class PropertyPaymentDetailPresenter extends BasePresenter<PropertyPaymentDetailContract.View> implements PropertyPaymentDetailContract.Presenter {

    @Override
    public void attach(PropertyPaymentDetailContract.View view) {
        super.attach(view);
    }

    @Override
    public void onBackBtnClick() {
        // Activity自我了结
        getView().closeActivity();
    }
}
