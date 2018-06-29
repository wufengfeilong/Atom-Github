package com.fcn.park.info.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.info.bean.DemandListBean;

/**
 * Created by liuyq on 2018/04/19.
 * 公告 新闻 活动 需求列表contract
 */

public interface InfoDemandListContract {

    interface View extends BaseView, RecyclerViewContract.View<DemandListBean.DemandsListBean>, PullToRefeshContract.View {
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {
    }

}
