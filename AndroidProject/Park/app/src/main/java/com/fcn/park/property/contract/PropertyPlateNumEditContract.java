package com.fcn.park.property.contract;

import com.fcn.park.base.BaseView;

/**
 * Created by 860115032 on 2018/04/19.
 */

public interface PropertyPlateNumEditContract {

    interface View extends BaseView {

        String getPlateNum();
    }

    interface Presenter {

        void onCancelClick();

        void onPositiveClick();

        void onEditTextChanged();
    }
}
