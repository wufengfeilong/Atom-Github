package com.fcn.park.manager.adapter;


import com.fcn.park.base.adapter.BindingBaseRecycleAdapter;
import com.fcn.park.base.adapter.BindingViewHolder;
import com.fcn.park.databinding.ItemLayoutPropertyFeeListBinding;
import com.fcn.park.manager.bean.PropertyFeeListBean;

/**
 * 物业费列表画面用Adapter.
 */
public class PropertyFeeListAdapter
        extends BindingBaseRecycleAdapter<PropertyFeeListBean.ListPropertyBean, ItemLayoutPropertyFeeListBinding> {

    public PropertyFeeListAdapter(int resLayoutId) {
        super(resLayoutId);
    }

    @Override
    public void bindingViews(BindingViewHolder<ItemLayoutPropertyFeeListBinding> holder, int position, PropertyFeeListBean.ListPropertyBean propertyFeeListBean) {
        holder.getBinding().setPropertyFeeListBean(propertyFeeListBean);
    }
}
