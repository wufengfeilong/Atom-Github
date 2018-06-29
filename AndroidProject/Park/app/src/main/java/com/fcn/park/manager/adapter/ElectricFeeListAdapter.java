package com.fcn.park.manager.adapter;


import com.fcn.park.base.adapter.BindingBaseRecycleAdapter;
import com.fcn.park.base.adapter.BindingViewHolder;
import com.fcn.park.databinding.ItemLayoutElectricFeeListBinding;
import com.fcn.park.manager.bean.ElectricFeeListBean;

/**
 * 电费列表画面用Adapter.
 */

public class ElectricFeeListAdapter extends BindingBaseRecycleAdapter<ElectricFeeListBean.ListElectricBean, ItemLayoutElectricFeeListBinding> {

    public ElectricFeeListAdapter(int resLayoutId) {
        super(resLayoutId);
    }

    @Override
    public void bindingViews(BindingViewHolder<ItemLayoutElectricFeeListBinding> holder, int position, ElectricFeeListBean.ListElectricBean electricFeeListBean) {
        holder.getBinding().setElectricFeeBean(electricFeeListBean);
    }
}
