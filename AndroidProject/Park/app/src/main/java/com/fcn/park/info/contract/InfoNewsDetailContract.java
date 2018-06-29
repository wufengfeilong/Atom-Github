package com.fcn.park.info.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.info.bean.InfoNewsBean;

import java.util.List;

/**
 * Created by liuyq on 2018/04/12.
 * 新闻 公告 活动详情用contract
 */

public interface InfoNewsDetailContract {

    interface View extends BaseView {
        void updateInfo(InfoNewsBean bean);

        String getId();
    }

    interface Presenter {
        void loadInfo();
    }

}
