package com.fcn.park.me.contract;

import android.content.Intent;

import com.fcn.park.base.BaseView;

/**
 * Created by 860117073 on 2018/4/27.
 */

public interface MeAddCarContract {

    interface View extends BaseView {
        //获取用户输入车主姓名
        String getCarOwner();
        //获取车牌号
        String getPlateNumber();
        //获取电话号码
        String getPhone();


    }
    interface Presenter {
        //用户点击提交
        void onClickSubmit();

    }
}
