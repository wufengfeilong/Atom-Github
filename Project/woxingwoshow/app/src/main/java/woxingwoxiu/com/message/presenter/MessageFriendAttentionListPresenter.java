package woxingwoxiu.com.message.presenter;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.message.bean.MessageFriendAttentionListBean;
import woxingwoxiu.com.message.contract.MessageFriendAttentionListContract;

/**
 *
 * 类描述：好友关注列表
 */

public class MessageFriendAttentionListPresenter
        extends BasePresenter<MessageFriendAttentionListContract.View> implements MessageFriendAttentionListContract.Presenter {
    List<MessageFriendAttentionListBean> mList=new ArrayList<>();

    @Override
    public void loadListData() {
        mList.clear();
        MessageFriendAttentionListBean bean1 =new MessageFriendAttentionListBean();
        bean1.setNickname("雷神");
        bean1.setSignature("关注：我就是雷神，没错");
        mList.add(bean1);
        MessageFriendAttentionListBean bean2 =new MessageFriendAttentionListBean();
        bean2.setNickname("洛基");
        bean2.setSignature("关注：我就是洛基，哈哈");
        mList.add(bean2);
        MessageFriendAttentionListBean bean3 =new MessageFriendAttentionListBean();
        bean3.setNickname("幻世");
        bean3.setSignature("关注：幻世就是我，恩额");
        mList.add(bean3);
        getView().bindListData(mList);
    }
    //返回按钮的点击事件
    @Override
    public void onClickBack(){
        getView().closeActivity();
    }
}
