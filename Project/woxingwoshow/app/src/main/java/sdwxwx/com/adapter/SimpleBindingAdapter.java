package sdwxwx.com.adapter;

import android.databinding.ViewDataBinding;

/**
 * create by 860115039
 * date      2018/5/8
 * time      9:00
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
