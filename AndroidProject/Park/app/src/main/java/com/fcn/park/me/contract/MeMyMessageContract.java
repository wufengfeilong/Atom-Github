package com.fcn.park.me.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.PullToRefeshContract;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.me.bean.MeMessageRecordBean;

/**
 * create by 860115039
 * date      2018/04/23
 * time      13:06
 * 个人中心-消息列表
 */
public interface MeMyMessageContract {
    interface View extends BaseView, RecyclerViewContract.View<MeMessageRecordBean.ListMessageBean>, PullToRefeshContract.View {
        /**
         * 带参数跳转画面
         */
        void jumpActivity(Class cl,String id);
        /**
         * 获取点击位置
         */
        int getClickPosition();
    }

    interface Presenter extends RecyclerViewContract.Presenter, PullToRefeshContract.Presenter {

    }
}
