package sdwxwx.com.home;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import cn.emay.sdk.util.json.gson.Gson;
import cn.emay.sdk.util.json.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.ActivityHomeBinding;
import sdwxwx.com.home.fragment.HomeFragment;
import sdwxwx.com.letter.activity.LetterListActivity;
import sdwxwx.com.login.model.LoginModel;
import sdwxwx.com.login.utils.ActivityCollector;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.fragment.MeHomeFragment;
import sdwxwx.com.message.fragment.MessageListFragment;
import sdwxwx.com.near.fragment.NearFragment;
import sdwxwx.com.release.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomePresenter> implements HomeContract.View, EMMessageListener {
    private static final String TAG = "HomeActivity";
    FragmentManager fm;
    Fragment mContent;
    List<Fragment> fragmentList;
    List<Integer> selectedImg;
    List<Integer> unSelectImg;
    List<ImageView> navBtmIv;
    private boolean flag_DND = false;

    @Override
    protected void initViews() {

        ViewUtils.setImgTransparent(this);

        mDataBinding.setPresenter(mPresenter);
        initFragment();
        initSelectedImg();
        initUnSelectImg();
        initNavBtm();
        setDefaultFragment();

        //关闭之前的所有活动
        ActivityCollector.finishAll();


        //环信登录
        easeMobe();

        //注册接受消息的监听
        EMClient.getInstance().chatManager().addMessageListener(this);
        //关闭之前的所有活动
        ActivityCollector.addActivity(this);

    }

    public void easeMobe() {

        //环信账户的名称
        final String easemob_username = LoginHelper.getInstance().getUserBean().getEasemob_username();
        //环信账户的密码
        final String easemob_password = LoginHelper.getInstance().getUserBean().getEasemob_password();

        //登录环信的回调
        EMClient.getInstance().login(easemob_username, easemob_password, new EMCallBack() {

            @Override
            public void onSuccess() {
                //确认入主页面后本地会话和群组都 load 完毕。
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d(TAG, "登录聊天服务器成功！");

            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "登录聊天服务器失败！");
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });


    }

    /**
     * 向通知栏发送消息
     *
     * @param bean
     * @param msg
     */
    public void sendNotification(UserBean bean, String msg) {
        String channelID = "1";

        String channelName = "channel_name";
        //获取PendingIntent
        Intent mainIntent = new Intent(this, LetterListActivity.class);
        mainIntent.putExtra("memberId", bean.getId());
        mainIntent.putExtra("EmId", bean.getEasemob_username());
        mainIntent.putExtra("FansHeadUrl", bean.getAvatar_url());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT>=26){
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            notification = new Notification.Builder(getApplicationContext(), channelID)
                    .setContentTitle("我行我秀")
                    .setContentText(bean.getNickname() + ":" + msg)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.logo)
                    .setAutoCancel(true)
                    .build();
        } else {
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.logo)//设置小图标
                    .setContentTitle("我行我秀")
                    .setContentText(bean.getNickname() + ":" + msg)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
        }
        notificationManager.notify(0, notification);
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {

  /*      //消息保存到数据库
        EMClient.getInstance().chatManager().importMessages(list);*/

        for (final EMMessage message : list) {
            if (TextUtils.isEmpty(((EMTextMessageBody) message.getBody()).getMessage().trim())) {
                return;
            }

            ((HomeFragment)fragmentList.get(0)).haveNewMsg();

            String memberId = message.getStringAttribute("memberId", null);
            memberId = memberId==null?message.ext().get("userId")+"":memberId;
            LoginModel.getMemberInfo(memberId, memberId, new BaseCallback<UserBean>() {
                @Override
                public void onSuccess(UserBean data) {
                    //是否面打扰判断
                    if (isDND().size() == 0) {
                        //sp里没有面打扰记录
                        sendNotification(data, ((EMTextMessageBody) message.getBody()).getMessage());
                    } else if (isDND().size() > 0) {
                        for (int i = 0; i < isDND().size(); i++) {
                            //匹配到面打扰用户 flag设置为true；
                            if ((data.getId().equals(isDND().get(i)))) {
                                flag_DND = true;
                            }
                        }
                        if (!flag_DND) {
                            sendNotification(data, ((EMTextMessageBody) message.getBody()).getMessage());
                        }

                    }

                    Log.d(TAG, ((EMTextMessageBody) message.getBody()).getMessage());
                }

                @Override
                public void onFail(String msg) {
                    showToast(msg);
                }
            });

        }
    }

    //获取设置了免打扰用户id 的list
    public List isDND() {
        List<String> DND_List = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("SP_DND_List", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        String DNDListJson = sp.getString("KEY_DND_LIST_DATA", "");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        if (DNDListJson != "")  //防空判断
        {
            Gson gson = new Gson();
            DND_List = gson.fromJson(DNDListJson, new TypeToken<List<String>>() {
            }.getType()); //将json字符串转换成List集合
        }
        return DND_List;
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

    private void initNavBtm() {
        navBtmIv = new ArrayList<>();
        navBtmIv.add(mDataBinding.homeBtmMenuHome);
        navBtmIv.add(mDataBinding.homeBtmMenuNear);
        navBtmIv.add(mDataBinding.homeBtmMenuMsg);
        navBtmIv.add(mDataBinding.homeBtmMenuMe);
    }

    private void clearSelectStatus() {
        for (int i = 0; i < 4; i++) {
            Glide.with(this).load(unSelectImg.get(i)).into(navBtmIv.get(i));
        }
    }

    private void setSelectStatus(int pos) {
        Glide.with(this).load(selectedImg.get(pos)).into(navBtmIv.get(pos));
    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        fragmentList.add(homeFragment);
        NearFragment nearFragment = new NearFragment();
        fragmentList.add(nearFragment);
        MessageListFragment messageListFragment = new MessageListFragment();
        fragmentList.add(messageListFragment);
        MeHomeFragment meHomeFragment = new MeHomeFragment();
        fragmentList.add(meHomeFragment);
    }

    private void initUnSelectImg() {
        unSelectImg = new ArrayList<>();
        unSelectImg.add(R.drawable.home_no_select);
        unSelectImg.add(R.drawable.near_no_select);
        unSelectImg.add(R.drawable.info_no_select);
        unSelectImg.add(R.drawable.me_no_select);
    }

    private void initSelectedImg() {
        selectedImg = new ArrayList<>();
        selectedImg.add(R.drawable.home_selected);
        selectedImg.add(R.drawable.near_selected);
        selectedImg.add(R.drawable.info_selected);
        selectedImg.add(R.drawable.me_selected);
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }


    @Override
    public void setDefaultFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        // update by lily
        // 获取要显示的FragmentIndex
        int intentIndex = getIntent().getIntExtra(Constant.BACK_HOME_KEY, 0);
        transaction.replace(R.id.home_content, fragmentList.get(intentIndex));
        transaction.commit();
        mContent = fragmentList.get(intentIndex);
        clearSelectStatus();
        setSelectStatus(intentIndex);

    }

    @Override
    public void setShowFragment(int position) {
//        if (!isLogin() && position == 3) {
//            actionStartActivity(LoginActivity.class);
//            return;
//        }
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.home_content, fragmentList.get(position));
        transaction.commit();
        clearSelectStatus();
        setSelectStatus(position);
    }


    //切换fragment的显示隐藏 不重新加载数据
    @Override
    public void switchContent(int position) {
        //        if (!isLogin() && position == 3) {
//            actionStartActivity(LoginActivity.class);
//            return;
//        }
        if (mContent != fragmentList.get(position)) {
            FragmentTransaction transaction = fm.beginTransaction();
            if (!fragmentList.get(position).isAdded()) {    // 先判断是否被add过
                transaction.hide(mContent).add(R.id.home_content, fragmentList.get(position)).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mContent).show(fragmentList.get(position)).commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = fragmentList.get(position);
        }
        clearSelectStatus();
        setSelectStatus(position);
    }

}
