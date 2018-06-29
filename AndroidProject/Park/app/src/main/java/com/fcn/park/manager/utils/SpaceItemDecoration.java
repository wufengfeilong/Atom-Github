package com.fcn.park.manager.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.fcn.park.R;
import com.fcn.park.manager.bean.SpaceBean;

import java.util.List;

/**
 * 类描述：根据类型不同，为RecyclerView添加间隔的ItemDecoration
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private static final int DEFAULT_VALUE_ITEM_DIVIDER_HEIGHT = 1;
    private static final int DEFAULT_VALUE_ITEM_DIVIDER_LEFT_MARGIN = 15;
    private static final int DEFAULT_VALUE_PARENT_SPACE_HEIGHT = 20;

    private List<? extends SpaceBean> mMenuBeanList;

    private int mItemDividerHeight;
    private int mItemDividerLeftMargin;
    private int mParentSpaceHeight;
    private Paint mDividerPaint;
    private Paint mParentSpacePaint;

    private Context mContext;
    private ColorStateList mSpaceColor;

    public SpaceItemDecoration(Context context, List<? extends SpaceBean> beanList) {

        mContext = context;

        mItemDividerHeight = DEFAULT_VALUE_ITEM_DIVIDER_HEIGHT;
        mItemDividerLeftMargin = DEFAULT_VALUE_ITEM_DIVIDER_LEFT_MARGIN;
        mParentSpaceHeight = DEFAULT_VALUE_PARENT_SPACE_HEIGHT;

        mMenuBeanList = beanList;
        mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mParentSpacePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSpaceColor = ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.colorMeMenuSpace));
        mDividerPaint.setColor(Color.parseColor("#FFCCCCCC"));
        updateSpaceColor();
        updateValues();
    }

    public SpaceItemDecoration setSpaceColor(ColorStateList color) {
        mSpaceColor = color;
        updateSpaceColor();
        return this;
    }

    public SpaceItemDecoration setSpaceColor(@ColorRes int color) {
        mSpaceColor = ColorStateList.valueOf(ContextCompat.getColor(mContext, color));
        updateSpaceColor();
        return this;
    }

    public SpaceItemDecoration setItemDecorationLeftMargin(int leftMargin) {
        mItemDividerLeftMargin = leftMargin;
        updateValues();
        return this;
    }

    public SpaceItemDecoration setSpaceHeight(int spaceHeight) {
        mParentSpaceHeight = spaceHeight;
        updateValues();
        return this;
    }

    private void updateSpaceColor() {
        mParentSpacePaint.setColor(mSpaceColor.getDefaultColor());
    }

    private void updateValues() {
        mItemDividerLeftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mItemDividerLeftMargin, mContext.getResources().getDisplayMetrics());
        mParentSpaceHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mParentSpaceHeight, mContext.getResources().getDisplayMetrics());
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
        if (position > -1) {
            if (position == 0) {
                outRect.set(0, 0, 0, mItemDividerHeight);
            } else {//其他的通过判断
                if (null != mMenuBeanList.get(position).getParentTag() && !mMenuBeanList.get(position).getParentTag().equals(mMenuBeanList.get(position - 1).getParentTag())) {
                    outRect.set(0, mParentSpaceHeight + mItemDividerHeight * 2, 0, 0);//不为空 且跟前一个tag不一样了，说明是新的分类，也要留出空白
                } else {
                    outRect.set(0, 0, 0, mItemDividerHeight);
                }
            }
        }
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        int parentHeight = parent.getHeight();
        int childSumHeight = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            childSumHeight += child.getHeight();
            //通过RecyclerView.LayoutParams获取当前ChildView的位置
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            //计算出当前ChildView的顶部位置和底部位置
            final int top = child.getTop() + params.topMargin;
            final int bottom = top + mItemDividerHeight;
            //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
            if (position > -1) {
                if (position == 0) {//等于0肯定要有title的
                    c.drawRect(left, top, right, bottom, mDividerPaint);
                } else {//其他的通过判断
                    if (null != mMenuBeanList.get(position).getParentTag() && !mMenuBeanList.get(position).getParentTag().equals(mMenuBeanList.get(position - 1).getParentTag())) {
                        int parentSpaceTop = top - mParentSpaceHeight;
                        int parentSpaceBottom = parentSpaceTop + mParentSpaceHeight;
                        //c.drawRect(left, parentSpaceTop - mItemDividerHeight, right, parentSpaceTop, mDividerPaint);
                        c.drawRect(left, parentSpaceTop, right, parentSpaceBottom, mParentSpacePaint);
                        //c.drawRect(left, top, right, bottom, mDividerPaint);
                    } else {
                        c.drawRect(left + mItemDividerLeftMargin, top, right, bottom, mDividerPaint);
                    }

                }
            }
        }
    }

}
