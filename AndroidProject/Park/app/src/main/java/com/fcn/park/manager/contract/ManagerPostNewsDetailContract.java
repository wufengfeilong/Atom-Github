package com.fcn.park.manager.contract;

import android.content.Context;
import android.support.annotation.StringDef;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.DetailInfoBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 管理中心的新闻详情用Contract.
 */

public interface ManagerPostNewsDetailContract {

    String INFO_TYPE_COMPANY_NEWS = "company";//企业新闻
    String INFO_TYPE_NEWS = "news"; // 新闻详情

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({INFO_TYPE_COMPANY_NEWS, INFO_TYPE_NEWS})
    @interface DetailInfoType {
    }

    interface View extends BaseView {
        void updateInfo(DetailInfoBean bean);
        String getId();
        String getNewsType();
    }

    interface Presenter {
        void loadInfo();
    }
}
