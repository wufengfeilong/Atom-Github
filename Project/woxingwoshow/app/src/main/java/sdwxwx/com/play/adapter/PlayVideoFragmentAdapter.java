package sdwxwx.com.play.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/23
 * time      15:19
 */
public class PlayVideoFragmentAdapter extends FragmentStatePagerAdapter {
    List<Fragment> mList;

    public PlayVideoFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mList.get(arg0);//显示第几个页面
    }

    @Override
    public int getCount() {
        return mList.size();//有几个页面
    }

}
