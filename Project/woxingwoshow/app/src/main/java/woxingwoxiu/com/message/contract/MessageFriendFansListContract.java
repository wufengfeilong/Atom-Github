package woxingwoxiu.com.message.contract;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.message.bean.MessageFriendFansListBean;

/**
 *
 * 类描述：好友粉丝列表
 */

public interface MessageFriendFansListContract {
    interface View extends BaseView,RecyclerViewContract.View<MessageFriendFansListBean> {

    }
    interface Presenter extends RecyclerViewContract.Presenter{
        void onClickBack();
    }
}
