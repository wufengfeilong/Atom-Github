package woxingwoxiu.com.letter.activity;


import android.support.v7.widget.LinearLayoutManager;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityLetterChatBinding;
import woxingwoxiu.com.letter.bean.PrivateLetterBean;
import woxingwoxiu.com.letter.contract.LetterChatContract;
import woxingwoxiu.com.letter.presenter.LetterChatPresenter;

public class LetterChatActivity extends BaseActivity<ActivityLetterChatBinding, LetterChatPresenter> implements LetterChatContract.View {
    public List<PrivateLetterBean.LetterBean> mLists;
    MultiItemTypeAdapter adapter;

    @Override
    protected LetterChatPresenter createPresenter() {
        return new LetterChatPresenter();
    }

    @Override
    protected void initViews() {
        initsSendData();

        mDataBinding.letterChatList.setAdapter(adapter);
    }

    public void initsSendData() {
        PrivateLetterBean.LetterBean bean1 = new PrivateLetterBean.LetterBean();
        bean1.setType(true);
        bean1.setLetterContent("我发了一条消息");
        mLists.add(bean1);
        PrivateLetterBean.LetterBean bean2 = new PrivateLetterBean.LetterBean();
        bean2.setType(false);
        bean2.setLetterContent("别人给我发了条消息");
        mDataBinding.letterChatList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MultiItemTypeAdapter(this, mLists);
        adapter.addItemViewDelegate(new MsgSendItemDelagate());
        adapter.addItemViewDelegate(new MsgComingItemDelagate());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_letter_chat;
    }

    public class MsgSendItemDelagate implements ItemViewDelegate<PrivateLetterBean.LetterBean> {
        @Override
        public int getItemViewLayoutId() {
            return R.layout.me_chat_item;
        }

        @Override
        public boolean isForViewType(PrivateLetterBean.LetterBean letterBean, int i) {
            return letterBean.isType();
        }

        @Override
        public void convert(ViewHolder holder, PrivateLetterBean.LetterBean letterBean, int i) {
            holder.setText(R.id.me_content, letterBean.getLetterContent());
        }
    }

    public class MsgComingItemDelagate implements ItemViewDelegate<PrivateLetterBean.LetterBean> {
        @Override
        public int getItemViewLayoutId() {
            return R.layout.they_chat_item;
        }

        @Override
        public boolean isForViewType(PrivateLetterBean.LetterBean letterBean, int i) {
            return letterBean.isType();
        }

        @Override
        public void convert(ViewHolder holder, PrivateLetterBean.LetterBean letterBean, int i) {
            holder.setText(R.id.they_content, letterBean.getLetterContent());
        }
    }

}
