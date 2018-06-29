package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.ManagerRepairsListBean;

/**
 * Created by 丁胜胜 on 2018/04/12.
 * 类描述：管理中心的报修一览用Contract
 */

public interface ManagerRepairsListContract {
            interface View extends BaseView, RecyclerViewContract.View<ManagerRepairsListBean.RepairsListBean>,PullToRefeshContract.View {

        //报修id取得
        String getRepairId();
        /**
         * 获取点击条目的位置
         * @return
         */
        int getClickPosition();

        //长按删除当前条目
        void deleteListItem(int position);

    }

    interface Presenter extends RecyclerViewContract.Presenter ,PullToRefeshContract.Presenter{

    }
}
