package com.fcn.park.home.parkinfo;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.ManagerParkIntroductionBean;

/**
 * Created by 860115001 on 2018/04/24.
 */

public interface ParkInfoContract {
    interface View extends BaseView {
        void updateInfo(ManagerParkIntroductionBean bean);
    }

    interface Presenter {
        void loadInfo(String park_id);
    }
}
