package com.fcn.park.base.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 860115001 on 2018/04/10.
 * 类描述：自定义标题栏
 */

public class TitleCompatLayout extends AppBarLayout {

    public TitleCompatLayout(Context context) {
        super(context);
        init(context);
    }

    public TitleCompatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {

        View statusBar = new View(context);
        statusBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        addView(statusBar);

    }
}
