package com.fcn.park.info.contract;

import com.fcn.park.base.BaseView;

/**
 * Created by liuyq  2018/04/09.
 * 园区动态fragment底层使用contract
 */

public interface InfoContract {
    interface View extends BaseView, ViewPagerWithTabContract.View {
    }

    interface Presenter extends ViewPagerWithTabContract.Presenter {
    }
}
