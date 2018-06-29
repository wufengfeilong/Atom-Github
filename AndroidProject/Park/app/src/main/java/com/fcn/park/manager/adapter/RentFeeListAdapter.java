package com.fcn.park.manager.adapter;


import com.fcn.park.base.adapter.BindingBaseRecycleAdapter;
import com.fcn.park.base.adapter.BindingViewHolder;
import com.fcn.park.databinding.ItemLayoutRentFeeListBinding;
import com.fcn.park.manager.bean.RentFeeListBean;

/**
 * 租赁列表画面用Adapter.
 */

public class RentFeeListAdapter extends BindingBaseRecycleAdapter<RentFeeListBean.ListRentBean, ItemLayoutRentFeeListBinding> {

    public RentFeeListAdapter(int resLayoutId) {
        super(resLayoutId);
    }

    @Override
    public void bindingViews(BindingViewHolder<ItemLayoutRentFeeListBinding> holder, int position, RentFeeListBean.ListRentBean rentFeeListBean) {
        holder.getBinding().setRentFeeListBean(rentFeeListBean);
    }
}
