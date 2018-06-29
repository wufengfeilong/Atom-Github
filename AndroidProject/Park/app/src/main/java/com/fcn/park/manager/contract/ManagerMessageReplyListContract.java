package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.ManagerMessageReplyListBean;
import com.fcn.park.manager.bean.ManagerRepairsListBean;

/**
 * Created by 丁胜胜 on 2018/04/25.
 * 类描述：管理中心的留言回复列表用Contract
 */

public interface ManagerMessageReplyListContract {
    interface View extends BaseView, RecyclerViewContract.View<ManagerMessageReplyListBean.ManagerReplyBean>,PullToRefeshContract.View {
        /**
         * 获取点击条目的位置
         * @return
         */
        int getClickPosition();
    }

    interface Presenter extends RecyclerViewContract.Presenter ,PullToRefeshContract.Presenter{

    }
}
