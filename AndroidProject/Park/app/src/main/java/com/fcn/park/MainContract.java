package com.fcn.park;

import com.fcn.park.base.BaseView;

/**
 * Created by 860115001 on 2018/04/02.
 */

public interface MainContract {

    interface View extends BaseView {
        void changeCurrentItem(int position);
    }

    interface Presenter {

    }
}
