package com.fcn.park.info.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.info.bean.NeedInfoBean;

/**
 * Created by liuyq on 2018/04/20.
 * 需求详情用contract——园区动态
 */

public interface InfoDemandDetailContract {
    interface View extends BaseView {
        void bindData(NeedInfoBean needInfoBean);

        String getNeedId();
    }

    interface Presenter {
        void loadInfo();
    }
}
