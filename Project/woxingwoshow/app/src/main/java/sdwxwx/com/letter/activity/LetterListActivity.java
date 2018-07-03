package sdwxwx.com.letter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.ActivityLetterListBinding;
import sdwxwx.com.home.bean.SearchUserBean;
import sdwxwx.com.letter.contract.LetterListContract;
import sdwxwx.com.letter.presenter.LetterListPresenter;
import sdwxwx.com.login.model.LoginModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.release.activity.FriendsListActivity;
import sdwxwx.com.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LetterListActivity extends BaseActivity<ActivityLetterListBinding, LetterListPresenter> implements LetterListContract.View, EMMessageListener {
    LetterListAdapter letterListAdapter;
    List<UserBean> mList = new ArrayList<>();
    public String lastMsg;
    String memberId = "";
    RefreshChat refreshChat;
    IntentFilter intentFilter;
    // 当前会话对象
    private EMConversation mConversation;
    private Map<String, String> msgMap = new HashMap<>();


    @Override
    protected LetterListPresenter createPresenter() {
        return new LetterListPresenter();
    }

    @Override
    protected void initViews() {
        //动态接受网络变化的广播接收器
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.sdwxwx.broadcast.close_chat");
        //注册我的广播
        refreshChat = new RefreshChat();
        registerReceiver(refreshChat, intentFilter);

        //注册接受消息的监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        mDataBinding.setPresenter(mPresenter);

        mDataBinding.letterList.setLayoutManager(new LinearLayoutManager(this));
        letterListAdapter = new LetterListAdapter(mList);
        mDataBinding.letterList.setAdapter(letterListAdapter);
        /**
         * 列表的点击事件
         */
        letterListAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(LetterListActivity.this, LetterChatActivity.class);
                intent.putExtra("memberId", mList.get(postion).getId());
                intent.putExtra("EmId", mList.get(postion).getEasemob_username());
                intent.putExtra("FansHeadUrl", mList.get(postion).getAvatar_url());
                intent.putExtra("Nickname", mList.get(postion).getNickname());
                startActivity(intent);
            }
        });

        ((ImageView) findViewById(R.id.add_chat)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LetterListActivity.this, FriendsListActivity.class), 0x001);
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();

        getAllConversations();
        //注册接受消息的监听
//        EMClient.getInstance().chatManager().addMessageListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消动态网络变化广播接收器的注册
        unregisterReceiver(refreshChat);
        //退出页面的时候移除listener
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    //接收删除回话的广播
    public class RefreshChat extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            msgMap.remove(intent.getStringExtra("removeMemberId"));
        }
    }

    public void getAllConversations() {
        mList.clear();
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        for (Map.Entry<String, EMConversation> entry : conversations.entrySet()) {

            //key为对方环信id
            String key = entry.getKey();
            //获取与此人聊天记录`
            mConversation = EMClient.getInstance().chatManager().getConversation(key, null, true);
            //获取最后一条消息
            EMMessage msg = mConversation.getLastMessage();

            //判断为空
            if (msg == null) {
                /* mDataBinding.letterList.removeOnItemTouchListener(listener);*/
                continue;
            } else {
                lastMsg = ((EMTextMessageBody) msg.getBody()).getMessage();
            }
            //判断传来的memberId是否为空
            memberId = msg.getStringAttribute("memberId", null);
            if (memberId == null) {
                memberId = msg.ext().get("userId") + "";
                if (memberId == null) {
                    memberId = "0";
                }
            }
            //判断传来的memberId是否为空
//            if (!TextUtils.isEmpty(Msg.getStringAttribute("memberId", null))) {
//                memberId = Msg.getStringAttribute("memberId", null);
//            } else {
//                memberId = "0";
//            }


            String toMemberId = msg.getStringAttribute("toMemberId", null);
            String targetMemberId = "0";

            if (memberId.equals(LoginHelper.getInstance().getUserId())) {
                msgMap.put(toMemberId, lastMsg);
                msgMap.put(toMemberId + "time", StringUtil.LongFormatTime(msg.getMsgTime()));
                targetMemberId = toMemberId;
            } else {
                msgMap.put(memberId, lastMsg);
                msgMap.put(memberId + "time", StringUtil.LongFormatTime(msg.getMsgTime()));
                targetMemberId = memberId;
            }
            LoginModel.getMemberInfo(LoginHelper.getInstance().getUserId(), targetMemberId, new BaseCallback<UserBean>() {
                @Override
                public void onSuccess(UserBean data) {
                    mList.add(data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            letterListAdapter.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void onFail(String msg) {

                }
            });
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                letterListAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                final SearchUserBean.HaveUserBean resultFriend =
                        (SearchUserBean.HaveUserBean) data.getSerializableExtra(Constant.CHOOSE_FRIEND);
                LoginModel.getMemberInfo(LoginHelper.getInstance().getUserId(), resultFriend.getId() + "", new BaseCallback<UserBean>() {
                    @Override
                    public void onSuccess(UserBean data) {
                        Intent intent = new Intent(LetterListActivity.this, LetterChatActivity.class);
                        intent.putExtra("memberId", data.getId());
                        intent.putExtra("EmId", data.getEasemob_username());
                        intent.putExtra("FansHeadUrl", data.getAvatar_url());
                        intent.putExtra("Nickname", data.getNickname());
                        startActivity(intent);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void finishThis() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_letter_list;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.letterList;
    }

    @Override
    public void initRecyclerView() {
    }


    public class LetterListAdapter extends BaseAdapter<UserBean> {
        public LetterListAdapter(List<UserBean> list) {
            super(R.layout.letter_list_item, list);

        }

        @Override
        protected void convert(BaseHolder holder, UserBean item) {
            super.convert(holder, item);
            //设置昵称
            holder.setText(R.id.letter_list_nick_name, item.getNickname());
            //设置消息
            holder.setText(R.id.letter_msg, msgMap.get(item.getId()));
            //时间设定
            holder.setText(R.id.letter_msg_time, msgMap.get(item.getId() + "time"));
            //头像设定
            Glide.with(LetterListActivity.this).load(item.getAvatar_url()).into((ImageView) holder.getView(R.id.letter_list_head));
        }

    }

    @Override
    public void bindListData(List<UserBean> beanList) {

    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {

        for (final EMMessage message : list) {
            String memberId = message.getStringAttribute("memberId", null);
            memberId = memberId == null ? message.ext().get("userId") + "" : memberId;
            lastMsg = ((EMTextMessageBody) message.getBody()).getMessage();

            //如果收到消息中有空格 不显示
            if (TextUtils.isEmpty(((EMTextMessageBody) message.getBody()).getMessage().trim())) {

            } else {
                if (msgMap.containsKey(memberId)) {
                    msgMap.put(memberId, lastMsg);
                    msgMap.put(memberId + "time", StringUtil.LongFormatTime(message.getMsgTime()));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            letterListAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    addNewMsg(memberId, lastMsg, StringUtil.LongFormatTime(message.getMsgTime()));

                }
            }

        }
    }

    public void addNewMsg(final String memberId, final String msg, final String time) {
        LoginModel.getMemberInfo(LoginHelper.getInstance().getUserId(), memberId, new BaseCallback<UserBean>() {
            @Override
            public void onSuccess(UserBean data) {
                mList.add(data);
                msgMap.put(memberId, msg);
                msgMap.put(memberId + "time", time);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        letterListAdapter.notifyDataSetChanged();

                    }
                });


            }

            @Override
            public void onFail(String msg) {

            }
        });

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

}
