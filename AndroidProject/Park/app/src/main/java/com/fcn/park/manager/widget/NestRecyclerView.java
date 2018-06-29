package com.fcn.park.manager.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 类描述：用于嵌套在ScrollView中使用，用于解决现实不全的问题
 */

public class NestRecyclerView extends RecyclerView {

    public NestRecyclerView(Context context) {
        super(context);
    }

    public NestRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, expandSpec);
    }
}
