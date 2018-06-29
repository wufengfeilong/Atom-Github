package com.fcn.park.info.holder;


import com.fcn.park.base.adapter.BindingBaseRecycleAdapter;
import com.fcn.park.base.adapter.BindingViewHolder;
import com.fcn.park.databinding.ItemLayoutDemandListBinding;
import com.fcn.park.info.bean.DemandListBean;

/**
 * Created by liuyq on 2017/04/19.
 * 类描述：需求列表的adapter
 */

public class InfoDemandListAdapter extends BindingBaseRecycleAdapter<DemandListBean.DemandsListBean, ItemLayoutDemandListBinding> {

    public InfoDemandListAdapter(int resLayoutId) {
        super(resLayoutId);
    }

    @Override
    public void bindingViews(BindingViewHolder<ItemLayoutDemandListBinding> holder, int position, DemandListBean.DemandsListBean demandsListBean) {
        holder.getBinding().setBean(demandsListBean);
    }

}
