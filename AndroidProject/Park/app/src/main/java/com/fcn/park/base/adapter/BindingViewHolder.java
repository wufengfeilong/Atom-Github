package com.fcn.park.base.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * @author liuyq on 2018/04/11

 */
public class BindingViewHolder<D extends ViewDataBinding>
        extends RecyclerView.ViewHolder {

    private D mBinding;

    public BindingViewHolder(D binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public D getBinding() {
        return mBinding;
    }
}
