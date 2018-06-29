package com.fcn.park.me;

import android.support.v7.view.menu.MenuItemImpl;

import com.fcn.park.R;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.manager.bean.MenuBean;

/**
 * Created by lvzp on 2017/3/1.
 * 类描述：我的Tab页菜单用module
 */

public class MeMenuModule extends MenuModuleIml<MenuBean> {
    /**
     * 获取菜单bean
     */
    @Override
    public MenuBean getMenuBean(MenuItemImpl menuItem) {
        if (Constant.UserType.OTHER.getValue().equals(LoginHelper.getInstance().getUserBean().getUserType())) {
            if (menuItem.getItemId() == R.id.manager_title_enterprise_check)
                return null;
        }
        return new MenuBean();
    }


}
