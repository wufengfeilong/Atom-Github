package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.ManagerPostNewsListBean;

import java.util.Map;

/**
 * 管理中心的新闻发布功能用Contract.
 */
public interface ManagerPostNewsAddContract {
    interface View extends BaseView {

        String getNewType();
        String getInputNewsTitle();
        String getInputNewsSources();
        String getInputNewsContent();

        boolean checkInputNewsTitleEmpty();
        boolean checkInputNewsSourcesEmpty();
        boolean checkInputNewsContentEmpty();

        ManagerPostNewsListBean.ListNewsBean getNewsBean();
        Map<String, String> getNewsThumbnail();
    }

    interface Presenter {
        void onClickNewsPublish();
    }
}
