package com.fcn.park.base.utils;

import android.support.design.widget.TabLayout;
import android.text.TextPaint;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class ViewUtils {

    public static void viewIn(View view) {
        if (view == null) return;
        view.setAnimation(AnimationUtils.makeInAnimation(view.getContext(), true));
        view.setVisibility(View.VISIBLE);
    }

    public static void viewOut(View view) {
        if (view == null) return;
        view.setAnimation(AnimationUtils.makeOutAnimation(view.getContext(), true));
        view.setVisibility(View.GONE);
    }

    /**
     * 获取TextView中文字的宽度
     *
     * @param tv
     * @return
     */
    public static float getTextWidth(TextView tv) {
        if (tv.getText() == null) return 0;
        return tv.getPaint().measureText(tv.getText().toString());
    }

    /**
     * 设置TabLayout的下标的宽度
     *
     * @param tabs
     */
    public static void setTabLayoutIndicatorWidth(TabLayout tabs) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        Field tabTextSize = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
            tabTextSize = tabLayout.getDeclaredField("mTabTextSize");
            tabStrip.setAccessible(true);
            tabTextSize.setAccessible(true);
            LinearLayout llTab = null;
            try {
                llTab = (LinearLayout) tabStrip.get(tabs);
                int childCount = llTab.getChildCount();
                int screenWidth = tabs.getWidth();
                int childWidth = screenWidth / childCount;
                for (int i = 0; i < childCount; i++) {
                    View child = llTab.getChildAt(i);
                    TabLayout.Tab tabAt = tabs.getTabAt(i);
                    int width = -1;
                    if (tabAt != null) {
                        TextPaint paint = new TextPaint();
                        paint.setTextSize((Float) tabTextSize.get(tabs));
                        width = (int) paint.measureText(tabAt.getText().toString());
                    }
                    int margin = (childWidth - width) / 2-10;
                    child.setPadding(0, 0, 0, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    params.leftMargin = margin;
                    params.rightMargin = margin;
                    child.setLayoutParams(params);
                    child.invalidate();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
