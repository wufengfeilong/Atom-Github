package woxingwoxiu.com.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/8
 * time      9:00
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;


    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragmentList(List<Fragment> fragmentList) {
        mFragments = fragmentList;
        notifyDataSetChanged();
    }

    public void addFragment(Fragment fragment) {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
        }
        mFragments.add(fragment);
        notifyDataSetChanged();
    }

    public void addFragmentWithTitle(List<Fragment> fragmentList, List<String> titleList) {
        mFragments = fragmentList;
        mTitles = titleList;
        notifyDataSetChanged();
    }

    public void addFragmentWithTitle(Fragment fragment, String title) {
        if (mTitles == null)
            mTitles = new ArrayList<>();
        addFragment(fragment);
        mTitles.add(title);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null || mTitles.isEmpty() ? "" : mTitles.get(position);
    }
}
