package sdwxwx.com.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/8
 * time      9:00
 */

public abstract class BindingBaseRecycleAdapter<T, D extends ViewDataBinding> extends RecyclerView.Adapter<BindingViewHolder<D>> {

    protected D mDataBinding;

    protected List<T> mListData;
    private int mResLayoutId;
    private LayoutInflater mLayoutInflater;

    public BindingBaseRecycleAdapter(int resLayoutId) {
        mResLayoutId = resLayoutId;
    }

    public void setupData(List<T> listData) {
        mListData = listData;
        notifyDataSetChanged();
    }

    public void cleanData() {
        if (mListData != null)
            mListData.clear();
        mListData = null;
    }

    public T getItem(int i) {
        return mListData == null ? null : mListData.get(i);
    }

    @Override
    public BindingViewHolder<D> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null)
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        D binding = DataBindingUtil.bind(mLayoutInflater.inflate(mResLayoutId, parent, false));
        return new BindingViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<D> holder, int position) {
        bindingViews(holder, position, getItem(position));
    }

    protected abstract void bindingViews(BindingViewHolder<D> holder, int position, T t);

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

}
