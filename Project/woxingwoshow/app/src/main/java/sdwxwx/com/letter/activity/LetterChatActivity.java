package sdwxwx.com.letter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.databinding.ActivityLetterChatBinding;
import sdwxwx.com.letter.adapter.ChatAdapterForRv;
import sdwxwx.com.letter.bean.LetterBean;
import sdwxwx.com.letter.contract.LetterChatContract;
import sdwxwx.com.letter.presenter.LetterChatPresenter;
import sdwxwx.com.login.utils.LoginHelper;

import java.util.ArrayList;
import java.util.List;


public class LetterChatActivity extends BaseActivity<ActivityLetterChatBinding, LetterChatPresenter> implements LetterChatContract.View, EMMessageListener {

    public static List<LetterBean> mDatas = new ArrayList<>();
    private ChatAdapterForRv adapter;
    //对方用户id
    protected String mMemberId;
    //对方环信id
    protected String mEmId;
    //我的环信id
    protected String meEmId;
    //我的用户id
    protected String meMemberId;
    //对方头像url
    protected String mFansHeadUrl;
    //我的头像url
    protected String mMeHeadUrl;
    //对方的昵称
    protected String Nickname;

    boolean flgMsg = true;
    // 当前会话对象
    private EMConversation mConversation;

    CloseChatReceiver mReceiver;

    @Override
    protected LetterChatPresenter createPresenter() {
        return new LetterChatPresenter();
    }

    @Override
    protected void initViews() {

        mDataBinding.setPresenter(mPresenter);
        //自动滚屏到底部
        mDataBinding.letterChatList.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mDataBinding.letterChatList.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDataBinding.letterChatList.scrollToPosition(adapter.getItemCount() - 1);
                        }

                    }, 100);
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mDataBinding.letterChatList.setLayoutManager(layoutManager);

        //获取对方用户id
        mMemberId = getIntent().getStringExtra("memberId");
        // 获取对方的环信ID
        mEmId = getIntent().getStringExtra("EmId");
        //获取对方头像URL
        mFansHeadUrl = getIntent().getStringExtra("FansHeadUrl");
        //获取我的环信
        meEmId = LoginHelper.getInstance().getUserBean().getEasemob_username();
        //获取我的id;
        meMemberId = LoginHelper.getInstance().getUserBean().getId();
        //对方的昵称
        Nickname = getIntent().getStringExtra("Nickname");
        //设置标题栏的昵称显示
        ((TextView) findViewById(R.id.chat_nickname)).setText(Nickname);
        //对话框处理逻辑
        chatDialog();

        adapter = new ChatAdapterForRv(this, mDatas);
        mDataBinding.letterChatList.setAdapter(adapter);

        //注册接受消息的监听
        EMClient.getInstance().chatManager().addMessageListener(this);
//        ActivityCollector.addActivity(this);
        //动态接受网络变化的广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sdwxwx.broadcast.close_chat");
        //注册我的广播
        mReceiver = new CloseChatReceiver();
        registerReceiver(mReceiver, intentFilter);
    }

    //接收删除回话的广播
    public class CloseChatReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

    public void getConversation() {
        //清空回话
        mDatas.clear();
        LoginHelper.getInstance().setLastShowTime(0L);
        //获取聊天记录
        mConversation = EMClient.getInstance().chatManager().getConversation(mEmId, null, true);
        if (mConversation != null) {
            // 把来自此用户的所有信息设置为已读。
            EMClient.getInstance().chatManager().getConversation(mEmId).markAllMessagesAsRead();
            int count = mConversation.getAllMessages().size();
            if (count < mConversation.getAllMsgCount() && count < 20) {
                // 获取已经在列表中的最上边的一条消息id
                String msgId = mConversation.getAllMessages().get(0).getMsgId();
                // 分页加载更多消息，需要传递已经加载的消息的最上边一条消息的id，以及需要加载的消息的条数
                mConversation.loadMoreMsgFromDB(msgId, 20 - count);
            }
            //获取此会话的所有消息
            List<EMMessage> messages = mConversation.getAllMessages();
            for (final EMMessage message : messages) {
                //判断如果记录有空格字符，代表清除过历史记录，不显示空消息
                if (TextUtils.isEmpty(((EMTextMessageBody) message.getBody()).getMessage().trim())) {
                } else if (message.getFrom().equals(meEmId)) {

                    sendData(((EMTextMessageBody) message.getBody()).getMessage(),message.getMsgTime());
                } else {
                    getData(((EMTextMessageBody) message.getBody()).getMessage(),message.getMsgTime());
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void chatDialog() {
        final EditText sendMsg = mDataBinding.sendLetterEdit;
        // 发送内容编辑不为空，可发送
        sendMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mDataBinding.sendLetterBtn.setEnabled(false);
                } else {
                    mDataBinding.sendLetterBtn.setEnabled(true);
                }
            }
        });

        Button sendBtn = mDataBinding.sendLetterBtn;
        //  点击提交按钮的监听
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断将发送的消息是否为空
                if (!TextUtils.isEmpty(sendMsg.getText().toString().trim())) {
                    /*画面发送*/
                    sendData(sendMsg.getText().toString(),System.currentTimeMillis());
                    /*逻辑发送*/
                    //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户id
                    //发送一条消息给对方
                    EMMessage message = EMMessage.createTxtSendMessage(sendMsg.getText().toString(), mEmId);
//                    message.setChatType(EMMessage.ChatType.Chat);
                    // 增加自己特定的属性
                    message.setAttribute("memberId", meMemberId);
                    message.setAttribute("toMemberId", mMemberId);
                    // IOS用
                    message.setAttribute("userId", meMemberId);
                    message.setAttribute("userName", meEmId);
                    message.setAttribute("userImage", mMeHeadUrl);
                    message.setAttribute("headImage", mMeHeadUrl);
                    message.setAttribute("name",LoginHelper.getInstance().getUserBean().getNickname());


                    //发送消息
                    EMClient.getInstance().chatManager().sendMessage(message);
                    final List<EMMessage> list = new ArrayList<>();
                    list.add(message);
                    //消息保存到数据库
                    EMClient.getInstance().chatManager().importMessages(list);

                    //自动滚到底部
                    mDataBinding.letterChatList.scrollToPosition(adapter.getItemCount() - 1);
                    sendMsg.setText("");
                }
            }
        });
    }

    //给对方发送消息 画面显示
    public void sendData(String msg,long time) {

        LetterBean msg1 = null;
        //我的头像
        mMeHeadUrl = LoginHelper.getInstance().getUserBean().getAvatar_url();
        //添加时间显示
        long lastTime = LoginHelper.getInstance().getLastShowTime();
        if (time-lastTime>1000*60*5) {
            LoginHelper.getInstance().setLastShowTime(time);
        } else {
            time = 0L;
        }
        //我的昵称
        msg1 = new LetterBean("", mMeHeadUrl, "", "" + msg,
                time, true);

        mDatas.add(msg1);
//        adapter = new ChatAdapterForRv(this, mDatas);
        //自动滚到底部
        mDataBinding.letterChatList.scrollToPosition(adapter.getItemCount() - 1);

    }

    //收到对方消息 画面显示
    public void getData(String msg,long time) {

        LetterBean msg1 = null;
        //添加时间显示
        long lastTime = LoginHelper.getInstance().getLastShowTime();
        if (time-lastTime>1000*60*5) {
            LoginHelper.getInstance().setLastShowTime(time);
        } else {
            time = 0L;
        }
        msg1 = new LetterBean(mMemberId, mFansHeadUrl, "", "" + msg,
                time, false);

        /*  Glide.with(getContext()).load(mFansHeadUrl).into( (ImageView)findViewById(R.id.they_head_pic));*/
        mDatas.add(msg1);
//        adapter = new ChatAdapterForRv(this, mDatas);
        //自动滚到底部
        mDataBinding.letterChatList.scrollToPosition(adapter.getItemCount() - 1);

    }


    public void setOnclick() {
       /* adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position)
            {
                Toast.makeText(LetterChatActivity.this, "Click:" + position , Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position)
            {
                Toast.makeText(LetterChatActivity.this, "LongClick:" + position , Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_letter_chat;
    }

    /**
     * 获取对方的会员ID
     *
     * @return
     */
    @Override
    public String getTargetMemberId() {
        return mMemberId;
    }

    /**
     * 获取对方的环信ID
     *
     * @return
     */
    @Override
    public String getTargetEmId() {
        return mEmId;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册接受消息的监听
//        EMClient.getInstance().chatManager().addMessageListener(this);

        //获取聊天记录
        getConversation();


    }


    //收到消息
    @Override
    public void onMessageReceived(final List<EMMessage> list) {

        for (final EMMessage message : list) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //判断是否是当前用户来的消息
                    if (message.getFrom().equals(mEmId)) {
                        // 同时把本条消息设置为已读
                        EMClient.getInstance().chatManager().getConversation(mEmId).markAllMessagesAsRead();
                        //如果收到消息中有空格 不显示
                        if (TextUtils.isEmpty(((EMTextMessageBody) message.getBody()).getMessage().trim())) {

                        } else {
                            getData(((EMTextMessageBody) message.getBody()).getMessage(),message.getMsgTime());
                            Log.d("msgok:", ((EMTextMessageBody) message.getBody()).getMessage());
                        }
                    }

                }
            });
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出页面的时候移除listener
        EMClient.getInstance().chatManager().removeMessageListener(this);
        //取消动态网络变化广播接收器的注册
        unregisterReceiver(mReceiver);
    }
}
