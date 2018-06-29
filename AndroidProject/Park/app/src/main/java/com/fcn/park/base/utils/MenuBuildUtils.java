package com.fcn.park.base.utils;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;

import java.util.List;

/**
 * 类描述：
 */

public class MenuBuildUtils {


    public static List<MenuItemImpl> buildMenuItemList(Context context, @MenuRes int menuRes) {
        MenuBuilder menu = inflateBuilder(context, menuRes);
        return menu.getVisibleItems();
    }


    public static MenuItemImpl getMenuItemForId(Context context, @MenuRes int menuRes, @IdRes int idRes) {
        MenuBuilder menu = inflateBuilder(context, menuRes);
        return (MenuItemImpl) menu.findItem(idRes);
    }

    @NonNull
    private static MenuBuilder inflateBuilder(Context context, @MenuRes int menuRes) {
        MenuBuilder menu = new MenuBuilder(context);
        new SupportMenuInflater(context).inflate(menuRes, menu);
        return menu;
    }
}
