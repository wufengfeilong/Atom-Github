package com.fcn.park.guide;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.fcn.park.MainActivity;
import com.fcn.park.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 */
public class GuideActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private List<Fragment> fragments;
    private ViewPager viewPager;
    private int[] images = {R.drawable.guide_center_1, R.drawable.guide_center_2, R.drawable.guide_center_3,};
    private GuideAdapter adapter;
    private Button bt_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        checkIsFirst();
        initViews();
        initListener();
    }

    /**
     * 判断APP是否是第一次启动
     */
    public void checkIsFirst(){
        SharedPreferences shared=getSharedPreferences("park", MODE_PRIVATE);
        boolean isFirst=shared.getBoolean("isFirst", true);
        SharedPreferences.Editor editor=shared.edit();
        if(isFirst){
            //第一次进入跳转
            editor.putBoolean("isFirst", false);
            editor.commit();
        }else{
            Intent in=new Intent(GuideActivity.this, MainActivity.class);
            startActivity(in);
            finish();
        }
    }

    /**
     * 界面初始化
     */
    public void initViews() {
        setContentView(R.layout.activity_guide);
        // 立即开启按钮
        bt_main = (Button) findViewById(R.id.bt_main);
        viewPager = (ViewPager) findViewById(R.id.vp_guide);

        fragments = new ArrayList<>();
        for(int i=0;i<images.length;i++){
            GuideFragment guide = new GuideFragment(images[i]);
            fragments.add(guide);
        }
        adapter = new GuideAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());


    }

    /**
     * 监听初始化
     */
    public void initListener() {
        // 立即开启按钮
        bt_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        viewPager.addOnPageChangeListener(this);
    }

    /**
     * 引导页滑动监听
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == images.length - 1) {
            //最后一个，实现动画浮现
            bt_main.setVisibility(View.VISIBLE);
        } else {
            bt_main.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 引导页适配器
     */
    public class GuideAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public GuideAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    /**
     * 引导页切换动画
     */
    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @SuppressLint("NewApi")
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            if (position < -1) {
                view.setAlpha(0);
            } else if (position <= 1) {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
            } else {
                view.setAlpha(0);
            }
        }
    }
}
