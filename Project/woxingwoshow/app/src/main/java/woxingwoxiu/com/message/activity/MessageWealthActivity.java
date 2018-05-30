package woxingwoxiu.com.message.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.MessageWealthBinding;
import woxingwoxiu.com.message.adapter.MessageWealthAdapter;
import woxingwoxiu.com.message.contract.MessageWealthContract;
import woxingwoxiu.com.message.fragment.MessageWealthFragment;
import woxingwoxiu.com.message.presenter.MessageWealthPresenter;
import woxingwoxiu.com.widget.ScrollViewPager;
import woxingwoxiu.com.widget.TabLayout;


public class MessageWealthActivity extends BaseActivity<MessageWealthBinding,MessageWealthPresenter>
        implements MessageWealthContract.View,TabLayout.OnTabSelectedListener {
    FragmentManager fm;
    List<Fragment> list;
    private TabLayout tabLayout;
    private ScrollViewPager viewPager;
    private List<String> tabs = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private MessageWealthAdapter messageWealthAdapter;

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
        //ScrollViewPager
        mDataBinding.setPresenter(mPresenter);
        tabLayout = mDataBinding.wealthFragmentTl;
        viewPager = mDataBinding.wealthFragmentVp;
        initFragment();
        initTabs();
        //注入标签
        tabLayout.addTab(tabLayout.newTab().setText("推荐财富"));

        tabLayout.addTab(tabLayout.newTab().setText("视频财富"));
        if(fragments.size()<=0){
            //设置TabLayout点击事件
            for (int i = 0; i < tabs.size(); i++) {
                fragments.add(MessageWealthFragment.newInstance(i));
            }
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
        MessageWealthFragment messageWealthFragment = MessageWealthFragment.newInstance(1);
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
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MessageWealthActivity.class);
        context.startActivity(intent);
    }
}
