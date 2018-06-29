package com.fcn.park.me.adapter;

import com.fcn.park.R;
import com.fcn.park.base.adapter.BindingBaseRecycleAdapter;
import com.fcn.park.base.adapter.BindingViewHolder;
import com.fcn.park.databinding.ItemLayoutPersonInfoMenuBinding;
import com.fcn.park.me.bean.InputMenuBean;

/**
 * create by 860115039
 * date      2018/04/17
 * time      9:28
 * 个人中心-个人信息编辑用adapter
 */
public class MePersonInfoAdapter extends BindingBaseRecycleAdapter<InputMenuBean, ItemLayoutPersonInfoMenuBinding> {

    public MePersonInfoAdapter() {
        super(R.layout.item_layout_person_info_menu);
    }

    @Override
    public void bindingViews(BindingViewHolder<ItemLayoutPersonInfoMenuBinding> holder, int position, InputMenuBean inputMenuBean) {
        holder.getBinding().setMenuBean(inputMenuBean);
    }


    public void updateAvatar(String imageUrl) {
        updateSingleItem(0, imageUrl);
    }

    public boolean updateSingleItem(int position, String value) {
        InputMenuBean bean = getItem(position);
        String menuValue = bean.getMenuValue();
        if (menuValue != null && menuValue.equals(value)) {
            return false;
        }
        bean.setMenuValue(value);
        notifyItemChanged(position);
        return true;
    }
}
