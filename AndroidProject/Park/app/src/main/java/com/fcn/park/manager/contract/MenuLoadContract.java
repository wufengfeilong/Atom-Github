package com.fcn.park.manager.contract;

import android.support.v7.view.menu.MenuItemImpl;

import com.fcn.park.manager.bean.MenuBean;

import java.util.List;

/**
 * Created by lvzp on 2017/3/2.
 * 类描述：
 */

public interface MenuLoadContract {

    interface View {
        List<MenuItemImpl> getMenuList();
    }

    /**
     * 不建议直接实现这个接口，建议继承它的子类{MenuModuleIml}
     *
     * @param <T>
     */
    interface Module<T extends MenuBean> {
        void attachMenuList(List<MenuItemImpl> menuItemList);

        T buildMenuBean(MenuItemImpl menuItem);

        List<T> getMenuBeanList();
    }

    interface Presenter {

        void initMenu();
    }

}
