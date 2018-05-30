package woxingwoxiu.com.home.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/9
 * time      13:48
 */
public class HomeCategoryAdapter extends FragmentPagerAdapter {
    List<String> tabs;
    List<Fragment> fragments = new ArrayList<>();

    public HomeCategoryAdapter(FragmentManager childFragmentManager, List<String> tabs, List<Fragment> fragments) {
        super(childFragmentManager);
        this.tabs =tabs;
        this.fragments = fragments;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }
}
