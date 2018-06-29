package com.fcn.park.info.contract;

import android.widget.RadioGroup;

import com.fcn.park.base.BaseView;

/**
 * Created by 860116014 on 2018/04/24.
 * 管理企业需求新增发布需求页面使用contract
 */

public interface ManagerDemandAddContract {
    interface View extends BaseView {

        String getDemandTitle();

        int getDemandCategory();

        String getDemandSource();

        String getDemandContact();

        String getDemandTel();

        String getDemandAddress();

        String getDemandDetailed();

        boolean checkTitleEmpty();

        boolean checkSourceEmpty();

        boolean checkContactEmpty();

        boolean checkTelEmpty();

        boolean checkAddressEmpty();

        boolean checkContentEmpty();

    }

    interface Presenter {
        void onClickDemandPublish();
    }

}
