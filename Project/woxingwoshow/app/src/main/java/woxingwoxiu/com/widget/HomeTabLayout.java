package woxingwoxiu.com.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;

import java.lang.reflect.Field;

/**
 * create by 860115039
 * date      2018/5/11
 * time      10:59
 */
public class HomeTabLayout extends TabLayout {
    private static final int TabViewNumber = 7;
    private static final String SCROLLABLE_TAB_MIN_WIDTH = "mScrollableTabMinWidth";
    public HomeTabLayout(Context context) {
        super(context);
        initTabMinWidth();
    }

    public HomeTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTabMinWidth();
    }

    public HomeTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTabMinWidth();
    }
    private void initTabMinWidth() {
        int screenWidth=getResources().getDisplayMetrics().widthPixels;
        int tabMinWidth = screenWidth / TabViewNumber;

        Field field;
        try {
            field = TabLayout.class.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH);
            field.setAccessible(true);
            field.set(this, tabMinWidth);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
