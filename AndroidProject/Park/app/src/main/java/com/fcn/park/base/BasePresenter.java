package com.fcn.park.base;

import android.support.v7.widget.RecyclerView;

import com.fcn.park.base.recycler_listener.OnRecyclerItemClickListener;

import java.lang.ref.WeakReference;

/**
 * 类描述：MVP模式的业务逻辑层。负责业务逻辑服务，是V层与M层间的桥梁
 */

public class BasePresenter<V extends BaseView> {

    private WeakReference<V> mWeakReferenceView;
    protected OnRecyclerItemClickListener mOnItemClickListener;

    public void attach(V view) {
        mWeakReferenceView = new WeakReference<>(view);
    }

    public void createItemClickListener(RecyclerView recyclerView) {
        mOnItemClickListener = new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                BasePresenter.this.onItemClick(vh, vh.getLayoutPosition());
            }
        };
    }

    public void deAttach() {
        if (mWeakReferenceView != null) {
            mWeakReferenceView.clear();
            mWeakReferenceView = null;
        }
    }

    public V getView() {
        return mWeakReferenceView != null ? mWeakReferenceView.get() : null;
    }
    public void onItemClick(RecyclerView.ViewHolder vh, int position) {
    }
}
