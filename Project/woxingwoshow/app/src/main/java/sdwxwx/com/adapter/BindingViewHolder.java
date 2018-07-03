package sdwxwx.com.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * create by 860115039
 * date      2018/5/8
 * time      9:00
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
