package com.fcn.park.manager.adapter;


import com.fcn.park.base.adapter.BindingBaseRecycleAdapter;
import com.fcn.park.base.adapter.BindingViewHolder;
import com.fcn.park.databinding.ItemLayoutWaterFeeListBinding;
import com.fcn.park.manager.bean.WaterFeeListBean;

/**
 * 水费列表画面用Adapter.
 */

public class WaterFeeListAdapter extends BindingBaseRecycleAdapter<WaterFeeListBean.ListWaterBean, ItemLayoutWaterFeeListBinding> {

    public WaterFeeListAdapter(int resLayoutId) {
        super(resLayoutId);
    }

    @Override
    public void bindingViews(BindingViewHolder<ItemLayoutWaterFeeListBinding> holder, int position, WaterFeeListBean.ListWaterBean waterFeeListBean) {
        holder.getBinding().setWaterFeeListBean(waterFeeListBean);
    }
}
