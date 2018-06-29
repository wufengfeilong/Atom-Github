package com.fcn.park.base.adapter;

import android.databinding.ViewDataBinding;

/**
 * Created by liuyq on 2018/04/11.
 * 类描述：
 */

public class SimpleBindingAdapter<T> extends BindingBaseRecycleAdapter<T, ViewDataBinding> {

    private int mVariable;

    public SimpleBindingAdapter(int resLayoutId, int variable) {
        super(resLayoutId);
        mVariable = variable;
    }

    @Override
    protected void bindingViews(BindingViewHolder<ViewDataBinding> holder, int position, T t) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(mVariable, t);
        binding.executePendingBindings();
    }
}
