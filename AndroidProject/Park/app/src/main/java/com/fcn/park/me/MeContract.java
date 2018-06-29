package com.fcn.park.me;

import com.fcn.park.base.BaseView;
import com.fcn.park.base.contract.RecyclerViewContract;
import com.fcn.park.manager.bean.MenuBean;
import com.fcn.park.manager.contract.MenuLoadContract;

/**
 * Created by 860115001 on 2018/04/03.
 */

public interface MeContract {
    interface View extends BaseView, RecyclerViewContract.View<MenuBean>, MenuLoadContract.View {
        /**
         * 是否有未读信息
         */
        void isMsg(boolean flg);
        /**
         * 带参数跳转画面
         */
        void jumpActivity(Class cl,String id);
    }

    interface Presenter extends RecyclerViewContract.Presenter, MenuLoadContract.Presenter {
        /**
         * 点击进入个人详情
         */
        void onClickGoProjectInfo();

    }
}
