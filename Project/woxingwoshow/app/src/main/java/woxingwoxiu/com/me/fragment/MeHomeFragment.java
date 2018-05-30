package woxingwoxiu.com.me.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseFragment;
import woxingwoxiu.com.databinding.MeHomeBinding;
import woxingwoxiu.com.me.adapter.MeHomeAdapter;
import woxingwoxiu.com.me.contract.MeHomeContract;
import woxingwoxiu.com.me.presenter.MeHomePresenter;
import woxingwoxiu.com.widget.ScrollViewPager;
import woxingwoxiu.com.widget.TabLayout;

/**
 *
 */
public class MeHomeFragment extends BaseFragment<MeHomeBinding, MeHomePresenter>
        implements MeHomeContract.View,TabLayout.OnTabSelectedListener{
    FragmentManager fm;
    List<Fragment> list;
    private TabLayout tabLayout;
    private ScrollViewPager viewPager;
    private List<String> tabs = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private MeHomeAdapter mMeHomeAdapter;

    @Override
    protected MeHomePresenter createPresenter() {
        return new MeHomePresenter();
    }


//    public TabLayout getTabLayout() {
//        return tabLayout;
//    }
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
        initFragment();
        initTabs();
        //注入标签
        tabLayout.addTab(tabLayout.newTab().setText("2"));
        tabLayout.addTab(tabLayout.newTab().setText("3"));
        if(fragments.size()<=0){
            //设置TabLayout点击事件
            for (int i = 0; i < tabs.size(); i++) {
                fragments.add(MeHomeVideoFragment.newInstance(i));
            }
        }
        tabLayout.addOnTabSelectedListener(this);

//        viewPager.setOffscreenPageLimit(tabs.size() - 1);//设置ViewPager的缓存界面数,默认缓存为2
        mMeHomeAdapter = new MeHomeAdapter(getFragmentManager(), tabs, fragments);
        viewPager.setAdapter(mMeHomeAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initTabs() {
//        tabLayout.addTab(tabLayout.newTab().setText("tab").setIcon(R.drawable.icon));
        tabs.add("我的视频");
        tabs.add("点赞视频");
    }

    private void initFragment() {
        fm = getFragmentManager();
        list = new ArrayList<>();
        MeHomeVideoFragment meHomeFragment = MeHomeVideoFragment.newInstance(1);
        list.add(meHomeFragment);
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
