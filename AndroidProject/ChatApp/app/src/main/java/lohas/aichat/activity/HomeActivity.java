package lohas.aichat.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import lohas.aichat.R;
import lohas.aichat.adapter.MyFragmentPagerAdapter;
import lohas.aichat.fragment.AddressFragment;
import lohas.aichat.fragment.ChatListFragment;
import lohas.aichat.fragment.FriendsFragment;
import lohas.aichat.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.btm_nav_bar)
    BottomNavigationBar btmNavBar;
    List<Fragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initBtmNavBar();
        initViewPager();
    }

    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new ChatListFragment());
        fragments.add(new AddressFragment());
        fragments.add(new FriendsFragment());
        fragments.add(new MineFragment());

        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                btmNavBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initBtmNavBar() {
        btmNavBar.setMode(BottomNavigationBar.MODE_FIXED);
        btmNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        btmNavBar.setActiveColor("#ff88ca2c");
        btmNavBar
                .addItem(new BottomNavigationItem(R.drawable.chat, "聊天"))
                .addItem(new BottomNavigationItem(R.drawable.address, "通讯录"))
                .addItem(new BottomNavigationItem(R.drawable.friends, "亲友圈"))
                .addItem(new BottomNavigationItem(R.drawable.my, "我"))
                .setFirstSelectedPosition(0)
                .initialise();
        btmNavBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }


}
