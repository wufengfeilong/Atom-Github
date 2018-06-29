package com.fcn.park.info.holder;


import com.fcn.park.base.adapter.BindingBaseRecycleAdapter;
import com.fcn.park.base.adapter.BindingViewHolder;
import com.fcn.park.databinding.ItemLayoutEnterpriseListBinding;
import com.fcn.park.info.bean.EnterpriseListBean;

/**
 * Created by liuyq on 2017/04/16.
 * 类描述：公司列表的adapter
 */

public class EnterpriseListAdapter extends BindingBaseRecycleAdapter<EnterpriseListBean.BusinesslistBean, ItemLayoutEnterpriseListBinding> {

    public EnterpriseListAdapter(int resLayoutId) {
        super(resLayoutId);
    }

    @Override
    public void bindingViews(BindingViewHolder<ItemLayoutEnterpriseListBinding> holder, int position, EnterpriseListBean.BusinesslistBean enterpriseListBean) {
        holder.getBinding().setBean(enterpriseListBean);
    }
}
