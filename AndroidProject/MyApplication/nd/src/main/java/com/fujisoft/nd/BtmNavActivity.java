package com.fujisoft.nd;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import android.support.v7.app.AppCompatActivity;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

public class BtmNavActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private ChatFragment chatFragment;
    private ResultFragment resultFragment;

    private BottomNavigationBar.OnTabSelectedListener mOnNavigationTabSelected
            = new BottomNavigationBar.OnTabSelectedListener() {

        @Override
        public void onTabSelected(int i) {
            FragmentManager fm = BtmNavActivity.this.getFragmentManager();
            //开启事务
            FragmentTransaction transaction = fm.beginTransaction();
            switch (i) {
                case 0:
                    if (homeFragment == null) {
                        homeFragment = HomeFragment.newInstance("Home");
                    }
                    transaction.replace(R.id.content, homeFragment);
                    break;
                case 1:
                    if (chatFragment == null) {
                        chatFragment = ChatFragment.newInstance("Chat");
                    }
                    transaction.replace(R.id.content, chatFragment);
                    break;
                case 2:
                    if (resultFragment == null) {
                        resultFragment = ResultFragment.newInstance("Result");
                    }
                    transaction.replace(R.id.content, resultFragment);
                    break;
                default:
                    break;
            }
            // 事务提交
            transaction.commit();
        }

        @Override
        public void onTabUnselected(int i) {

        }

        @Override
        public void onTabReselected(int i) {

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btm_nav);

        BottomNavigationBar navigation = (BottomNavigationBar) findViewById(R.id.navigation);
        navigation.setMode(BottomNavigationBar.MODE_FIXED);
        navigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        TextBadgeItem numberBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorAccent)
                .setText("5")
                .setHideOnSelect(true);

        navigation
                .addItem(new BottomNavigationItem(R.drawable.nav_2, "检测").setActiveColor(Color.RED))
//                .addItem(new BottomNavigationItem(R.drawable.nav_3, "聊天").setActiveColor(Color.GREEN).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.nav_1, "聊天").setActiveColor(Color.GREEN).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.nav_4, "结果").setActiveColor(Color.CYAN))
                .initialise();
        navigation.setTabSelectedListener(mOnNavigationTabSelected);
        setDefaultFragment();

    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = HomeFragment.newInstance("Home");
        transaction.replace(R.id.content, homeFragment);
        transaction.commit();
    }

}
