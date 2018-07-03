package sdwxwx.com.message.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * 类描述：我的团队一级二级列表
 */
public class MessageMyTeamAdapter extends FragmentPagerAdapter {
    List<String> tabs;
    List<Fragment> fragments = new ArrayList<>();

    public MessageMyTeamAdapter(FragmentManager childFragmentManager, List<String> tabs, List<Fragment> fragments) {
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
