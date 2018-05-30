package woxingwoxiu.com.message.contract;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.message.bean.MessageFriendAttentionListBean;

/**
 *
 * 类描述：好友关注列表
 */

public interface MessageFriendAttentionListContract {
    interface View extends BaseView,RecyclerViewContract.View<MessageFriendAttentionListBean> {

    }
    interface Presenter extends RecyclerViewContract.Presenter{
        void onClickBack();
    }
}
