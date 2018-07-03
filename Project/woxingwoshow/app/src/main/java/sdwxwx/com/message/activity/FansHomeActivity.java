package sdwxwx.com.message.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.EMContactListener;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import sdwxwx.com.R;
import sdwxwx.com.adapter.BaseAdapter;
import sdwxwx.com.adapter.BaseHolder;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.ActivityFansHomeBinding;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.login.model.LoginModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.message.contract.FansHomeContract;
import sdwxwx.com.message.presenter.FansHomePresenter;
import sdwxwx.com.recycler_listener.OnRecyclerItemClickListener;
import sdwxwx.com.util.AvatarScanHelper;
import sdwxwx.com.util.MeterUtil;
import sdwxwx.com.util.StringUtil;
import sdwxwx.com.widget.OnekeyShare;
import sdwxwx.com.widget.ShareFrag;

import java.util.ArrayList;
import java.util.List;

/**
 * 粉丝主页
 */
public class FansHomeActivity extends BaseActivity<ActivityFansHomeBinding, FansHomePresenter> implements FansHomeContract.View, EMContactListener {

    FansVideoAdapter fansVideoAdapter;
    List<PlayVideoBean> mList = new ArrayList<>();
    int page = 1;
    /**
     * 前一画面传递的会员ID
     */
    private String memberId;
    private String mEmId;
//    private String mFansHeadUrl = "";
    private String urlAddr = "";
    private String avatar_url = "";
    private String Nickname;
    LoadMoreFansListReceiver mReceiver;
    boolean isFresh;
    @Override
    protected FansHomePresenter createPresenter() {
        return new FansHomePresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
        fansVideoAdapter = new FansVideoAdapter(mList);
        mDataBinding.fansVideoSpringView.setListener(this);
        mDataBinding.fansVideoSpringView.setHeader(new DefaultHeader(this));
        mDataBinding.fansVideoSpringView.setFooter(new DefaultFooter(this));
        mDataBinding.fansVideoList.setLayoutManager(new GridLayoutManager(FansHomeActivity.this, 3));
        mDataBinding.fansVideoList.addOnItemTouchListener(new OnItemClickListener(mDataBinding.fansVideoList));
        mDataBinding.fansVideoList.setAdapter(fansVideoAdapter);
        // 取得前一画面的会员ID
        memberId = getIntent().getStringExtra(Constant.INTENT_PARAM);
        //加载他人详情信息和视频列表数据
        mPresenter.loadListData(Constant.REQUEST_PAGE);

        //动态接受网络变化的广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sdwxwx.load.fans.video.list");
        intentFilter.addAction("com.sdwxwx.thumb.fans");
        //注册我的广播
        mReceiver = new LoadMoreFansListReceiver();
        getContext().registerReceiver(mReceiver, intentFilter);
    }

    public class LoadMoreFansListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.sdwxwx.load.fans.video.list")) {
                onLoadmore();
            } else {
                isFresh = true;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_fans_home;
    }

    @Override
    public View getAttention() {
        return mDataBinding.noAttention;
    }

    @Override
    public View cancelAttention() {
        return mDataBinding.hasAttention;
    }

    @Override
    public void onShare() {
        // 设定URL地址
        urlAddr = Constant.HTTP_BASE_HOST + "recommend/register?recommender=" + memberId;

        // 调用会员详情接口，取得相关会员详情
        LoginModel.getMemberInfo(memberId, memberId, new BaseCallback<UserBean>() {
            @Override
            public void onSuccess(UserBean bean) {
                if (bean == null) {
                    showToast("会员不存在，请确认！");
                } else {
                    android.app.FragmentManager fm = getFragmentManager();
                    ShareFrag shareFrag = new ShareFrag();
                    OnekeyShare webBean = new OnekeyShare();
                    // 设定web网页分享的URL地址
                    webBean.setLayoutId(R.layout.web_page_share);
                    webBean.setShareType(Platform.SHARE_WEBPAGE);
                    // titel
                    webBean.setTitle("分享我在我行我秀的个人主页，快来一起玩吧～");
                    // 文字内容
                    webBean.setText("[" + bean.getNickname() + "]也在我行我秀，快来看TA的精彩作品吧！[" + bean.getNickname() + "]上传了" + bean.getVideo_count() + "个视频作品，一起来围观>>" + urlAddr);
                    // 分享者头像设定
                    webBean.setImageUrl(bean.getAvatar_url());
                    // TODO 暂定URL地址
                    webBean.setTitleUrl(urlAddr);
                    webBean.setUrl(urlAddr);
                    // 一下内容为QQ控件使用
                    webBean.setSite("我行我秀");
                    // TODO 暂定URL地址
                    webBean.setSiteUrl(urlAddr);
                    shareFrag.setShareParamsMap(webBean.getParams());
                    shareFrag.show(fm, null);
                }
            }

            @Override
            public void onFail(String msg) {
                showToast("由于政策因素，您暂时无法对外分享");
            }
        });
    }

    @Override
    public void bindListData(List<PlayVideoBean> beanList) {
        mList.addAll(beanList);
//        fansVideoAdapter = new FansVideoAdapter(mList);
        LoginHelper.getInstance().setFansList(mList);
        if (mList.size()<=0) {
            mDataBinding.fansVideoSpringView.setVisibility(View.GONE);
        } else {
            mDataBinding.fansVideoSpringView.setVisibility(View.VISIBLE);
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fansVideoAdapter.notifyDataSetChanged();
            }
        });
        mDataBinding.fansVideoSpringView.onFinishFreshAndLoad();
        Intent intent = new Intent("com.sdwxwx.load.video.list.end");
        getContext().sendBroadcast(intent);
    }

    /**
     * 调用获取会员详情后把数据设定到画面
     *
     * @param mbean
     */
    @Override
    public void bindFansInfor(UserBean mbean) {
        mEmId = mbean.getEasemob_username();
//        mFansHeadUrl = mbean.getAvatar_url();
        Nickname = mbean.getNickname();
        String  FansBirthday = mbean.getBirthday();
        //判断生日为空不显示
        if (!FansBirthday.equals("0000-00-00")){
            //生日
            mDataBinding.fansBirthday.setText(FansBirthday);
        }
        // 头像设定
        RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
        Glide.with(getContext()).load(mbean.getAvatar_url()).apply(options).into(mDataBinding.fansHead);
        avatar_url = mbean.getAvatar_url();
        //VIP
        if ("1".equals(mbean.isCertified())) {
            mDataBinding.homeFansVipHead.setVisibility(View.VISIBLE);
        }
        TextView fansTitle = (TextView) findViewById(R.id.fans_name);
        //标题昵称
        fansTitle.setText(mbean.getNickname());
        //粉丝数
        mDataBinding.fansFollowedCount.setText(MeterUtil.numToWan(Integer.parseInt(mbean.getFollowed_count())) );
        //关注数
        mDataBinding.fansFollowCount.setText(MeterUtil.numToWan(Integer.parseInt(mbean.getFollow_count())) );
        //团队数
        mDataBinding.fansRecommendCount.setText(MeterUtil.numToWan(Integer.parseInt(mbean.getRecommend_count())) );
        //财富值
        mDataBinding.allWealth.setText(MeterUtil.numToWan(Integer.parseInt(mbean.getAll_wealth())) );
        //性别
        mDataBinding.fansGender.setText(mbean.getGender());
        //视频数
        mDataBinding.fansVideoCount.setText(MeterUtil.numToWan(Integer.parseInt(mbean.getVideo_count())) );
        //个性签名
        if (!TextUtils.isEmpty(mbean.getSignature())) {
            mDataBinding.fansSign.setText(mbean.getSignature());
        }
        //获取城市列表根据
        mDataBinding.fansPosition.setText(mbean.getCity_id());
        // 根据关注关系，设置相关图片
        if ("1".equals(mbean.isFollowed())) {
            // 关注的图片不再表示
            mDataBinding.noAttention.setVisibility(View.GONE);
            // 显示发私信图片
            mDataBinding.hasAttention.setVisibility(View.VISIBLE);
        } else {
            // 发私信图片不再表示
            mDataBinding.hasAttention.setVisibility(View.GONE);
            // 显示关注的图片
            mDataBinding.noAttention.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initRecyclerView() {

    }

    @Override
    public RecyclerView getRecyclerView() {
        return mDataBinding.fansVideoList;
    }

    @Override
    public void onContactAdded(String s) {
        Log.d("frd:", "addok");
    }

    @Override
    public void onContactDeleted(String s) {
        Log.d("frd:", "del");
    }

    @Override
    public void onContactInvited(String s, String s1) {
        Log.d("frd:", "Invited");
    }

    @Override
    public void onFriendRequestAccepted(String s) {
        Log.d("frd:", "beagree");
    }

    @Override
    public void onFriendRequestDeclined(String s) {

    }

    @Override
    public void onRefresh() {
        mList.clear();
        page = 1;
        mPresenter.loadListData(Constant.REQUEST_PAGE);
    }

    @Override
    public void onLoadmore() {
        page = page + 1;
        mPresenter.loadListData(page+"");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFresh) {
            fansVideoAdapter.notifyDataSetChanged();
            isFresh = false;
        }
    }

    private void onLoadCurPageData(){
        mPresenter.loadListData(page+"");
    }

    public class FansVideoAdapter extends BaseAdapter<PlayVideoBean> {
        public FansVideoAdapter(List<PlayVideoBean> list) {
            super(R.layout.fans_video_item, list);
        }

        @Override
        protected void convert(BaseHolder holder, PlayVideoBean item) {
            holder.setText(R.id.fans_home_video_release_time, StringUtil.dataFormat(item.getCreate_time()));
            holder.setText(R.id.fans_home_video_good_count, item.getLike_count() + "");
            Glide.with(getContext()).load(item.getCover_url()).into((ImageView) holder.getView(R.id.fans_home_video_item_thumbnail));
            if ("0".equals(item.getIs_liked())) {
                holder.setImageResource(R.id.fans_home_video_thumb_iv, R.drawable.thumb_up_no_selected);
            } else {
                holder.setImageResource(R.id.fans_home_video_thumb_iv, R.drawable.video_liked);
            }
        }
    }

    /**
     * 列表的点击事件
     */
    private class OnItemClickListener extends OnRecyclerItemClickListener {
        public OnItemClickListener(RecyclerView recyclerView) {
            super(recyclerView);
        }

        @Override
        public void onItemClick(RecyclerView.ViewHolder vh) {
            mPresenter.onItemClick(vh, vh.getLayoutPosition());
        }
    }

    /**
     * 获取要迁移的会员ID
     *
     * @return
     */
    public String getMemberId() {
        return memberId;
    }

    @Override
    public String getEmId() {
        return mEmId;
    }

    @Override
    public String getFansHeadUrl() {
        return avatar_url;
    }

    @Override
    public String getNickname() {
        return Nickname;
    }

    public void clickHederImg() {
        new AvatarScanHelper(this, avatar_url);
    }

    @Override
    public SpringView getSpringView() {
        return mDataBinding.fansVideoSpringView;
    }
}
