package com.fcn.park.manager.contract;

import android.support.annotation.StringDef;
import android.support.v7.widget.RecyclerView;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.DetailInfoBean;
import com.fcn.park.manager.bean.ManagerRepairsDetailInfoBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by 丁胜胜 on 2018/04/24.
 * 类描述：管理中心的报修详情用Contract
 */

public interface ManagerRepairsDetailContract {

    interface View extends BaseView {

        void updateInfo(ManagerRepairsDetailInfoBean bean);

        //获取报修列表id
        String getRepairId();
        //获取报修者手机号
        String getRepairPhone();
    }

    interface Presenter {
        //加载数据
        void loadInfo();
        // 点击手机号的click事件
        void telCallOnClick();
    }
}

