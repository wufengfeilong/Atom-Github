package woxingwoxiu.com.message.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import woxingwoxiu.com.BR;
import woxingwoxiu.com.R;
import woxingwoxiu.com.adapter.SimpleBindingAdapter;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.MessageFriendAttentionListActivityBinding;
import woxingwoxiu.com.message.bean.MessageFriendAttentionListBean;
import woxingwoxiu.com.message.contract.MessageFriendAttentionListContract;
import woxingwoxiu.com.message.presenter.MessageFriendAttentionListPresenter;

/**
 *
 * 类描述：好友关注列表
 */
public class MessageFriendAttentionListActivity
        extends BaseActivity<MessageFriendAttentionListActivityBinding,MessageFriendAttentionListPresenter>
        implements MessageFriendAttentionListContract.View{
    SimpleBindingAdapter<MessageFriendAttentionListBean> mSimpleBindingAdapter;
    @Override
    protected MessageFriendAttentionListPresenter createPresenter() {
        return new MessageFriendAttentionListPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        mSimpleBindingAdapter = new SimpleBindingAdapter<>(R.layout.item_message_friend_attention_list_activity, BR.Bean);
        mPresenter.loadListData();
        mDataBinding.attentionList.setLayoutManager(new LinearLayoutManager(MessageFriendAttentionListActivity.this));
        mDataBinding.attentionList.addItemDecoration(new DividerItemDecoration(MessageFriendAttentionListActivity.this, DividerItemDecoration.VERTICAL));
        mDataBinding.attentionList.setAdapter(mSimpleBindingAdapter);
    }
    @Override
    public void bindListData(List<MessageFriendAttentionListBean> beanList) {
        mSimpleBindingAdapter.setupData(beanList);
    }
    @Override
    public void initRecyclerView() {

    }
    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.attentionList;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.message_friend_attention_list_activity;
    }

}
