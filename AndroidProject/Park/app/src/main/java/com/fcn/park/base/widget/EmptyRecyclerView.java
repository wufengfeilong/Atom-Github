package com.fcn.park.base.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liuyq on 2018/04/11.
 * 类描述：
 */

public class EmptyRecyclerView extends RecyclerView {

    private AdapterDataObserver mObserver = new MyDataObserver();
    private View mEmptyView;
    private ViewGroup mRefreshView;

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        final Adapter adapter = getAdapter();
        final boolean empty = ((adapter == null) || adapter.getItemCount() == 0);
        //updateEmptyStatus(empty);
    }

    public void setRefrshView(ViewGroup view) {
        mRefreshView = view;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        adapter.registerAdapterDataObserver(mObserver);
    }

    private class MyDataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            onDataChange();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            onDataChange();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            onDataChange();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            onDataChange();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            onDataChange();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            onDataChange();
        }

        private void onDataChange() {
            int itemCount = getAdapter().getItemCount();
            boolean empty;
            empty = itemCount <= 0;
            if (mEmptyView != null)
                updateEmptyStatus(empty);
        }

    }

    private void updateEmptyStatus(boolean empty) {
        if (empty) {
            /*if (getVisibility() == GONE)
                return;*/
            if (mEmptyView != null) {
                mEmptyView.setVisibility(VISIBLE);
            }
            if (mRefreshView != null)
                mRefreshView.setVisibility(GONE);
            setVisibility(GONE);
        } else {
          /*  if (getVisibility() == VISIBLE)
                return;*/
            if (mEmptyView != null) {
                mEmptyView.setVisibility(GONE);
            }
            setVisibility(VISIBLE);
            if (mRefreshView != null)
                mRefreshView.setVisibility(VISIBLE);
        }
    }
}
