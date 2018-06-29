package com.fcn.park.property.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.property.bean.PropertyMainBean;

import java.util.List;

/**
 * Created by 860115032 on 2018/04/11.
 */

public interface PropertyPaymentListContract {

    interface View extends BaseView {
        void setData(List<PropertyMainBean> list);
    }

    interface Presenter {

        void dataInit(int payType);
//
//        void onPayClick();

    }
}
