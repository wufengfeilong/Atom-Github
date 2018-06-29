package com.fcn.park.info.contract;

import com.fcn.park.base.BaseView;

/**
 * Created by liuyq on 2018/04/26.
 * 需求编辑页面用contract
 */

public interface ManagerDemandEditContract {
    interface View extends BaseView {
        String getDemandId();

        String getDemandEditTitle();

        String getDemandEditSource();

        String getDemandEditContact();

        String getDemandEditTel();

        String getDemandEditAddress();

        String getDemandEditDetailed();

        boolean checkTitleEditEmpty();

        boolean checkSourceEditEmpty();

        boolean checkContactEditEmpty();

        boolean checkTelEditEmpty();

        boolean checkAddressEditEmpty();

        boolean checkContentEditEmpty();

    }

    interface Presenter {
        void onClickDemandEdit();
    }
}
