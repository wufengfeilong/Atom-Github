package sdwxwx.com.me.fragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import sdwxwx.com.R;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BaseFragment;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.MeHomeBinding;
import sdwxwx.com.login.model.LoginModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.adapter.MeHomeAdapter;
import sdwxwx.com.me.contract.MeHomeContract;
import sdwxwx.com.me.presenter.MeHomePresenter;
import sdwxwx.com.util.AvatarScanHelper;
import sdwxwx.com.util.LoginUtil;
import sdwxwx.com.widget.OnekeyShare;
import sdwxwx.com.widget.ScrollViewPager;
import sdwxwx.com.widget.ShareFrag;
import sdwxwx.com.widget.TabLayout;

/**
 *
 */
public class MeHomeFragment extends BaseFragment<MeHomeBinding, MeHomePresenter>
        implements MeHomeContract.View, TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ScrollViewPager viewPager;
    private List<String> tabs = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private MeHomeAdapter mMeHomeAdapter;
    /**
     * 会员信息是否已经取得
     */
    private boolean flag = false;
    private String urlAddr = "";
    /**
     * 是否可以进行分享
     */
    private boolean shareFlag = false;
    /**
     * 头像地址
     */
    private String avatar_url = "";


    @Override
    protected MeHomePresenter createPresenter() {
        return new MeHomePresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.me_home;
    }

    @Override
    protected void initViews() {
        //ScrollViewPager
        mDataBinding.setPresenter(mPresenter);
        tabLayout = mDataBinding.meHomeFragmentTl;
        viewPager = mDataBinding.meHomeFragmentVp;
        if (fragments.size() <= 0) {
            //设置TabLayout点击事件
            for (int i = 0; i < 2; i++) {
                MeHomeVideoFragment fragment = MeHomeVideoFragment.newInstance(i);
                fragments.add(fragment);
            }
        }
        tabLayout.addOnTabSelectedListener(this);

//        viewPager.setOffscreenPageLimit(tabs.size() - 1);//设置ViewPager的缓存界面数,默认缓存为2
        mMeHomeAdapter = new MeHomeAdapter(getChildFragmentManager(), fragments, mContext);
        viewPager.setAdapter(mMeHomeAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mMeHomeAdapter.getTabView(i));
            }
        }
        tabLayout.getTabAt(0).getCustomView().setSelected(true);

        // 调用接口，更新画面表示的数据
        getMemberInfo();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public void setMeVideoCount(String count) {
        View view = tabLayout.getTabAt(0).getCustomView();
        TextView tv = view.findViewById(R.id.me_home_video_tv);
        tv.setText(count);
    }


    public void setUpVideoCount(String count) {
        View view = tabLayout.getTabAt(1).getCustomView();
        TextView tv = view.findViewById(R.id.me_home_video_tv);
        tv.setText(count);
    }

    /**
     * 点击进行分享
     */
    public void onShare() {
        // 设定URL地址
        urlAddr = Constant.HTTP_BASE_HOST + "recommend/register?recommender=" + LoginHelper.getInstance().getUserId();
        // 调用会员详情接口，取得相关会员详情
        LoginModel.getMemberInfo(String.valueOf(LoginHelper.getInstance().getUserId()), String.valueOf(LoginHelper.getInstance().getUserId()), new BaseCallback<UserBean>() {
            @Override
            public void onSuccess(UserBean bean) {
                if (bean == null) {
                    showToast("由于政策因素，您暂时无法对外分享");
                } else {
                    android.app.FragmentManager fm = getActivity().getFragmentManager();

                    ShareFrag shareFrag = new ShareFrag();
                    OnekeyShare webBean = new OnekeyShare();
                    // 设定web网页分享的URL地址
                    webBean.setLayoutId(R.layout.web_page_share);
                    webBean.setShareType(Platform.SHARE_WEBPAGE);
                    // titel
                    webBean.setTitle("分享我在我行我秀的个人主页，快来一起玩吧～");
                    // 文字内容
                    webBean.setText("[" + bean.getNickname() + "]也在我行我秀，快来看TA的精彩作品吧！" + "[" + bean.getNickname() + "]上传了" + bean.getVideo_count() + "个视频作品，一起来围观>>" + urlAddr);
                    // 分享者头像设定
                    webBean.setImageUrl(bean.getAvatar_url());
                    webBean.setTitleUrl(urlAddr);
                    webBean.setUrl(urlAddr);
                    // 一下内容为QQ控件使用
                    webBean.setSite("我行我秀");
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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        flag = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (flag) {
            getMemberInfo();
        }
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            getMemberInfo();
//        }
//    }

    /**
     * 设置画面
     */
    private void getMemberInfo() {
        // 获取会员ID
        String memberId = LoginHelper.getInstance().getUserId();
        // 调用获取会员的详情信息接口
        LoginModel.getMemberInfo(memberId, memberId, new BaseCallback<UserBean>() {
            // 如果成功则更新画面标识内容
            @Override
            public void onSuccess(UserBean data) {
                // 头像设定
                RequestOptions options = new RequestOptions().error(R.drawable.default_header_image);
                Glide.with(mContext).load(data.getAvatar_url()).apply(options).into(
                        mDataBinding.homeVideoItemHead);
                avatar_url = data.getAvatar_url();
                // 头像VIP
                if ("1".equals(data.isCertified())) {
                    mDataBinding.homeVideoVipHead.setVisibility(View.VISIBLE);
                }
                // 昵称设定
                mDataBinding.meTitleUserName.setText(data.getNickname());
                // 粉丝数
                mDataBinding.followedCount.setText(data.getFollowed_count());
                // 关注数
                mDataBinding.followCount.setText(data.getFollow_count());
                // 团队数
                mDataBinding.recommendCount.setText(data.getRecommend_count());
                // 财富值
                mDataBinding.sumWealth.setText(data.getAll_wealth());
                // 个性签名
                if (!TextUtils.isEmpty(data.getSignature())) {
                    mDataBinding.signature.setText(data.getSignature());
                }
                // 城市
                mDataBinding.cityName.setText(LoginHelper.getInstance().getCityName());
                // 我发布的视频数
                setMeVideoCount(data.getVideo_count());
                // 我点赞的视频数
                setUpVideoCount(data.getFavorite_video_count());
                // 更新缓存中数据
                LoginHelper.getInstance().setUserBean(data);
                LoginUtil.setUserBean(mContext, data);
            }

            // 如果错误，则提示错误信息
            @Override
            public void onFail(String msg) {
                showToast(msg);
            }
        });
    }

    public void clickHederImg() {
        new AvatarScanHelper(mContext, avatar_url);
    }
}
