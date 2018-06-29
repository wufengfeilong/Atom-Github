package com.fcn.park.manager.bean;

import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;

/**
 * 类描述：
 */

public class MenuBean extends SpaceBean {

    private Drawable menuIcon;//菜单的图标
    private int menuIconBg;//菜单的背景
    private String menuName;//菜单的名称
    private String menuValue;//菜单的value（一般的菜单显示不需要）
    private int menuTextColor;//菜单文本颜色
    private int position;//该菜单item的位置
    private int rId;//为当前的菜单item定义的唯一id
    private String sqlName;//网络数据库中存储的名称
    private String updateValue;//上传到网络中的数据
    private boolean isCheckable;//判断是否需要检查将上传的属性替换为Code码

    public boolean isCheckable() {
        return isCheckable;
    }

    public void setCheckable(boolean checkable) {
        isCheckable = checkable;
    }

    public String getUpdateValue() {
        return updateValue;
    }

    public void setUpdateValue(String updateValue) {
        this.updateValue = updateValue;
    }

    public String getMenuValue() {
        return menuValue;
    }

    public void setMenuValue(String menuValue) {
        this.menuValue = menuValue;
    }

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public int getMenuTextColor() {
        return menuTextColor;
    }

    public void setMenuTextColor(int menuTextColor) {
        this.menuTextColor = menuTextColor;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Drawable getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(Drawable menuIcon) {
        this.menuIcon = menuIcon;
    }

    public int getMenuIconBg() {
        return menuIconBg;
    }

    public void setMenuIconBg(int menuIconBg) {
        this.menuIconBg = menuIconBg;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getId() {
        return rId;
    }

    public void setId(@IdRes int id) {
        this.rId = id;
    }
}
