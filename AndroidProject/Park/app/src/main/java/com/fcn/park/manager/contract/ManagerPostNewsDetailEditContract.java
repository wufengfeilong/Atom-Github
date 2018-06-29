package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.ManagerPostNewsListBean;

import java.util.Map;

/**
 * 管理中心的新闻编辑功能用Contract.
 */

public interface ManagerPostNewsDetailEditContract {
    interface View extends BaseView {
        String getNewsId();
        String getInputNewsTitle();
        String getInputNewsSources();
        String getInputNewsContent();

        boolean checkInputNewsTitleEmpty();
        boolean checkInputNewsSourcesEmpty();
        boolean checkInputNewsContentEmpty();

        ManagerPostNewsListBean.ListNewsBean getNewsDetailBean();
        Map<String, String> getNewsDetailThumbnail();

    }
    interface Presenter {
        void onClickNewsDetailEdit();
    }
}
