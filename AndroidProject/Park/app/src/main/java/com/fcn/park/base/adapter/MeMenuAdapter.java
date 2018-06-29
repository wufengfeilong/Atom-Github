package com.fcn.park.base.adapter;

import com.fcn.park.base.adapter.BindingBaseRecycleAdapter;
import com.fcn.park.base.adapter.BindingViewHolder;
import com.fcn.park.databinding.ItemLayoutMeMenuBinding;
import com.fcn.park.manager.bean.MenuBean;

/**
 * 类描述：
 */
public class MeMenuAdapter extends BindingBaseRecycleAdapter<MenuBean, ItemLayoutMeMenuBinding> {

    public MeMenuAdapter(int resLayoutId) {
        super(resLayoutId);
    }

    @Override
    public void bindingViews(BindingViewHolder<ItemLayoutMeMenuBinding> holder, int position, MenuBean menuBean) {
        ItemLayoutMeMenuBinding binding = holder.getBinding();
        binding.setMenuBean(menuBean);
        binding.ivMenuIcon.setImageDrawable(menuBean.getMenuIcon());

    }
}
