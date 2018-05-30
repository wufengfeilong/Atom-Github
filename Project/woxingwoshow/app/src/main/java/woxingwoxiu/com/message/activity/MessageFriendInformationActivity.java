package woxingwoxiu.com.message.activity;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.MessageFriendInformationActivityBinding;
import woxingwoxiu.com.message.contract.MessageFriendInformationContract;
import woxingwoxiu.com.message.presenter.MessageFriendInformationPresenter;
/**
 *
 * 类描述：好友资料
 */
public class MessageFriendInformationActivity
        extends BaseActivity<MessageFriendInformationActivityBinding,MessageFriendInformationPresenter>
        implements MessageFriendInformationContract.View{
    @Override
    protected MessageFriendInformationPresenter createPresenter() {
        return new MessageFriendInformationPresenter();
    }
   //初始化
    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_friend_information_activity;
    }
}
