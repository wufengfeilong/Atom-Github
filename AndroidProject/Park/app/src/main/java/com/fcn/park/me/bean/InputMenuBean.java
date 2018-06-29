package com.fcn.park.me.bean;

import com.fcn.park.manager.bean.MenuBean;

/**
 * Created by lvzp on 2017/3/2.
 * 类描述：
 */

public class InputMenuBean extends MenuBean {

    private boolean isNeedInput;//判断是否需要输入
    private boolean isImage;//判断当前的菜单是否为图片
    private boolean isEdit;//判断当前的菜单是否处于编辑状态


    public boolean isNeedInput() {
        return isNeedInput;
    }

    public void setNeedInput(boolean needInput) {
        isNeedInput = needInput;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }
}
