package com.fcn.park.me;

import android.support.v7.view.menu.MenuItemImpl;

import com.fcn.park.manager.bean.MenuBean;
import com.fcn.park.manager.contract.MenuLoadContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvzp on 2017/3/8.
 * 类描述：
 */

public abstract class MenuModuleIml<T extends MenuBean> implements MenuLoadContract.Module<T> {

    private T mCurrentClickMenuBean;
    private int mCurrentClickPosition;

    protected List<T> mMenuBeanList;

    public MenuModuleIml() {
        mMenuBeanList = new ArrayList<>();
    }

    /**
     * 将从xml文件中读取出来的{@link MenuItemImpl}列表数据，填装到{@link #mMenuBeanList}中
     * 如果相对列表中的某一条的内容进行修改，可以在子类中重写这个方法，{@link #mMenuBeanList}进行修改
     *
     * @param menuItemList 这个是外界调用的时候传入的具体xml中的菜单数据
     */
    @Override
    public void attachMenuList(List<MenuItemImpl> menuItemList) {
        for (MenuItemImpl menuItem : menuItemList) {
            T bean = buildMenuBean(menuItem);
            if (bean == null) continue;
            mMenuBeanList.add(bean);
        }
        menuItemList.clear();
    }

    @Override
    public T buildMenuBean(MenuItemImpl menuItem) {
        T bean = getMenuBean(menuItem);
        if (bean == null) return null;
        bean.setId(menuItem.getItemId());
        bean.setParentTag("" + menuItem.getGroupId());
        bean.setMenuName(menuItem.getTitle().toString());
        bean.setCheckable(menuItem.isCheckable());
        bean.setSqlName(menuItem.getTitleCondensed().toString());
        bean.setMenuIcon(menuItem.getIcon());
        return bean;
    }

    @Override
    public List<T> getMenuBeanList() {
        return mMenuBeanList;
    }

    /**
     * 获取菜单实体类的具体对象
     *
     * @param menuItem
     * @return
     */
    public abstract T getMenuBean(MenuItemImpl menuItem);

    /**
     * 根据传入的Position获取当前条目的实体类
     *
     * @param position
     * @return
     */
    public T getItemBeanForPosition(int position) {
        mCurrentClickPosition = position;
        mCurrentClickMenuBean = mMenuBeanList.get(position);
        return mCurrentClickMenuBean;
    }

    /**
     * 获取当前点击的条目
     *
     * @return
     */
    public int getCurrentClickPosition() {
        return mCurrentClickPosition;
    }

    /**
     * 获取当前点击的对象
     *
     * @return
     */
    public T getCurrentClickMenuBean() {
        return mCurrentClickMenuBean;
    }

}
