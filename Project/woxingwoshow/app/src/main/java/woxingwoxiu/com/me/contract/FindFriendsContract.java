package woxingwoxiu.com.me.contract;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.me.bean.FindFriendsBean;

/**
 * Created by 860117073 on 2018/5/22.
 */

public interface FindFriendsContract {
    interface View extends BaseView,RecyclerViewContract.View<FindFriendsBean> {
    }

    interface Presenter extends RecyclerViewContract.Presenter{
        void myQrCodeClick();
    }
}
