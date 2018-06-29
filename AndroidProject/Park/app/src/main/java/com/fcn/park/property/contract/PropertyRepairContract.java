package com.fcn.park.property.contract;

import android.view.View;

import com.fcn.park.base.BaseView;
import com.fcn.park.property.bean.PropertyRepairBean;

import java.util.Map;

/**
 * Created by 860117073 on 2018/4/11.
 */

public interface PropertyRepairContract {
    interface View extends BaseView {

        Map<String, String> getImageMap();

        //获取用户输入的值
        String getRepairName();

        String getRepairPhone();

        String getRepairAddress();

        String getRepairContent();


        //选择图片/拍照 的popupwindow
        void openSelectPhotoPop1(android.view.View view);

        void openSelectPhotoPop2(android.view.View view);

        void openSelectPhotoPop3(android.view.View view);
    }

    interface Presenter {
        //用户点击提交
        void onClickSubmit();

    }
}
