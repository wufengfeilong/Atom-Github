package sdwxwx.com.me.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.R;

/**
 *
 * 类描述：
 */
public class MeHomeAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments = new ArrayList<>();
    private Context context;

    public MeHomeAdapter(FragmentManager childFragmentManager, List<Fragment> fragments,Context context) {
        super(childFragmentManager);
        this.context = context;
        this.fragments = fragments;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.me_home_video_tv);
        ImageView img = (ImageView) v.findViewById(R.id.me_home_video_iv);
        if (position==0) {
            tv.setText("0");
            img.setImageResource(R.drawable.me_home_tab0_selector);
        }else {
            tv.setText("10");
            img.setImageResource(R.drawable.me_home_tab1_selector);
        }
        return v;
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
