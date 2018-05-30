package woxingwoxiu.com.message.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.MessageMyTeamActivityBinding;
import woxingwoxiu.com.message.adapter.MessageMyTeamAdapter;
import woxingwoxiu.com.message.contract.MessageMyTeamContract;
import woxingwoxiu.com.message.fragment.MessageMyTeamFragment;
import woxingwoxiu.com.message.presenter.MessageMyTeamPresenter;
import woxingwoxiu.com.widget.ScrollViewPager;
import woxingwoxiu.com.widget.TabLayout;

public class MessageMyTeamActivity extends BaseActivity<MessageMyTeamActivityBinding,MessageMyTeamPresenter>
        implements MessageMyTeamContract.View,TabLayout.OnTabSelectedListener {
    FragmentManager fm;
    List<Fragment> list;
    private TabLayout tabLayout;
    private ScrollViewPager viewPager;
    private List<String> tabs = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private MessageMyTeamAdapter messageMyTeamAdapter;

    @Override
    protected MessageMyTeamPresenter createPresenter() {
        return new MessageMyTeamPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_my_team_activity;
    }

    @Override
    protected void initViews() {
        //ScrollViewPager
        mDataBinding.setPresenter(mPresenter);
        tabLayout = mDataBinding.teamFragmentTl;
        viewPager = mDataBinding.teamFragmentVp;
        initFragment();
        initTabs();
        //注入标签
        tabLayout.addTab(tabLayout.newTab().setText("一级"));
        tabLayout.addTab(tabLayout.newTab().setText("二级"));
        if(fragments.size()<=0){
            //设置TabLayout点击事件
            for (int i = 0; i < tabs.size(); i++) {
                fragments.add(MessageMyTeamFragment.newInstance(i));
            }
        }
        tabLayout.addOnTabSelectedListener(this);

//        viewPager.setOffscreenPageLimit(tabs.size() - 1);//设置ViewPager的缓存界面数,默认缓存为2
        messageMyTeamAdapter = new MessageMyTeamAdapter(getSupportFragmentManager(), tabs, fragments);
        viewPager.setAdapter(messageMyTeamAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initTabs() {
        tabs.add("一级");
        tabs.add("二级");
    }

    private void initFragment() {
        fm = getSupportFragmentManager();
        list = new ArrayList<>();
        MessageMyTeamFragment messageMyTeamFragment = MessageMyTeamFragment.newInstance(1);
        list.add(messageMyTeamFragment);
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
        Intent intent = new Intent(context, MessageMyTeamActivity.class);
        context.startActivity(intent);
    }
}
