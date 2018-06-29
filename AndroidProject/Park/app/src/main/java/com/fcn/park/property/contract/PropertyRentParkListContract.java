package com.fcn.park.property.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.property.bean.PropertyMainBean;

import java.util.List;

/**
 * 绿色物管的月租车辆审核列表画面用Contract
 */

public interface PropertyRentParkListContract {
    interface View extends BaseView {

        void setData(List<PropertyMainBean> list);

        void deleteItemFromView(int position);
    }

    interface Presenter {

        /**
         * 从服务器获取列表信息
         * @param moveType 画面跳转来源
         */
        void dataInit(int moveType);

        /**
         * 从服务器删除这条记录
         * @param parkPayId
         *         月租车辆申请表的ID
         * @param position
         *         ListView的操作位置
         */
        void deleteItemFromServer(int parkPayId, final int position);

        void onAddMenuClick();
    }

}
