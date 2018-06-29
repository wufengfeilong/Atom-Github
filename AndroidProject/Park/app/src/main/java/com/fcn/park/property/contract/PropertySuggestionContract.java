package com.fcn.park.property.contract;

import com.fcn.park.base.BaseView;

import java.util.Map;

/**
 * Created by 860117073 on 2018/4/24.
 */

public interface PropertySuggestionContract {
    interface View extends BaseView {
        //获取用户输入的值
        String getDescribeContent();

    }
    interface Presenter {
        //用户点击提交
       void onClickSubmit();
    }
}
