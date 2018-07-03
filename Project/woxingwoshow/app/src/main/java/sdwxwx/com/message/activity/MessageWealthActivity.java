package sdwxwx.com.message.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.MessageWealthBinding;
import sdwxwx.com.message.adapter.MessageWealthAdapter;
import sdwxwx.com.message.contract.MessageWealthContract;
import sdwxwx.com.message.fragment.MessageRecommendWealthFragment;
import sdwxwx.com.message.fragment.MessageWealthFragment;
import sdwxwx.com.message.presenter.MessageWealthPresenter;
import sdwxwx.com.release.utils.ViewUtils;
import sdwxwx.com.widget.ScrollViewPager;
import sdwxwx.com.widget.TabLayout;


public class MessageWealthActivity extends BaseActivity<MessageWealthBinding,MessageWealthPresenter>
        implements MessageWealthContract.View,TabLayout.OnTabSelectedListener {
    FragmentManager fm;
    List<Fragment> list;
    private TabLayout tabLayout;
    private ScrollViewPager viewPager;
    private List<String> tabs = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private MessageWealthAdapter messageWealthAdapter;

    /** 前一画面传递的会员ID */
    private String memberId;
    private String avatar_url;
    @Override
    protected MessageWealthPresenter createPresenter() {
        return new MessageWealthPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_wealth;
    }

    @Override
    protected void initViews() {
        ViewUtils.setImgTransparent(this);

        //ScrollViewPager
        mDataBinding.setPresenter(mPresenter);
        tabLayout = mDataBinding.wealthFragmentTl;
        viewPager = mDataBinding.wealthFragmentVp;
        initFragment();
        initTabs();
        if(fragments.size()<=0){
            //设置TabLayout点击事件
//            for (int i = 0; i < tabs.size(); i++) {
//                fragments.add(MessageWealthFragment.newInstance(i));
//            }
            // 取得前一画面的会员ID
            memberId = getIntent().getStringExtra(Constant.INTENT_PARAM);
            avatar_url = getIntent().getStringExtra(Constant.INTENT_PARAM_ONE);

            fragments.add(MessageRecommendWealthFragment.newInstance(0,memberId,Constant.SP_AVATAR_URL));
            fragments.add(MessageWealthFragment.newInstance(1,memberId,avatar_url));

        }
        tabLayout.addOnTabSelectedListener(this);

//        viewPager.setOffscreenPageLimit(tabs.size() - 1);//设置ViewPager的缓存界面数,默认缓存为2
        messageWealthAdapter = new MessageWealthAdapter(getSupportFragmentManager(), tabs, fragments);
        viewPager.setAdapter(messageWealthAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void initTabs() {
        tabs.add("推荐财富");
        tabs.add("视频财富");
    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        list = new ArrayList<>();
        MessageWealthFragment messageWealthFragment = MessageWealthFragment.newInstance(1,memberId,avatar_url);
        list.add(messageWealthFragment);
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

}
