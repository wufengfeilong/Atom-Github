package sdwxwx.com.message.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.MessageMyTeamActivityBinding;
import sdwxwx.com.message.adapter.MessageMyTeamAdapter;
import sdwxwx.com.message.contract.MessageMyTeamContract;
import sdwxwx.com.message.fragment.MessageMyTeamFragment;
import sdwxwx.com.message.presenter.MessageMyTeamPresenter;
import sdwxwx.com.release.utils.ViewUtils;
import sdwxwx.com.widget.ScrollViewPager;
import sdwxwx.com.widget.TabLayout;

public class MessageMyTeamActivity extends BaseActivity<MessageMyTeamActivityBinding,MessageMyTeamPresenter>
        implements MessageMyTeamContract.View,TabLayout.OnTabSelectedListener {
    FragmentManager fm;
    List<Fragment> list;
    private TabLayout tabLayout;
    private ScrollViewPager viewPager;
    private List<String> tabs = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private MessageMyTeamAdapter messageMyTeamAdapter;
    /** 前一画面传递的会员ID */
    private String memberId;
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
        ViewUtils.setImgTransparent(this);

        //ScrollViewPager
        mDataBinding.setPresenter(mPresenter);
        tabLayout = mDataBinding.teamFragmentTl;
        viewPager = mDataBinding.teamFragmentVp;
        initFragment();
        initTabs();
        if(fragments.size()<=0){
            //设置TabLayout点击事件
            for (int i = 0; i < tabs.size(); i++) {
                // 取得前一画面的会员ID
                memberId = getIntent().getStringExtra(Constant.INTENT_PARAM);
                fragments.add(MessageMyTeamFragment.newInstance(i,memberId));
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
        MessageMyTeamFragment messageMyTeamFragment = MessageMyTeamFragment.newInstance(1,memberId);
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
}
