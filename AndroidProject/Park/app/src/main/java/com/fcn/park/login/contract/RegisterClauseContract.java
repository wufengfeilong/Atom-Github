package com.fcn.park.login.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.login.bean.RegisterClauseBean;

/**
 * Created by 860115001 on 2018/04/10.
 */

public interface RegisterClauseContract {

    interface View extends BaseView {
        void bindWebData(RegisterClauseBean bean);

        void showLoadingProgress();

        void hintLoadingProgress();

        void updateLoadingProgress(int progress);
    }

    interface Presenter {
        void loadPageData();
    }
}
