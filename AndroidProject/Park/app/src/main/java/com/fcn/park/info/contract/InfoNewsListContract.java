package com.fcn.park.info.contract;


import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.info.bean.NewsListBean;

/**
 * Created by liuyq on 2017/04/10.
 * 类描述：取得新闻列表活动列表公告列表使用contract
 */

public interface InfoNewsListContract {

    interface View extends BaseView, RecyclerViewContract.View<NewsListBean.GetlistNewsBean>, PullToRefeshContract.View {
        String getNewsId();
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
    }

}
