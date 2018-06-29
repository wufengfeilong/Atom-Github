package com.fcn.park.property.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.property.bean.PropertyParkPayBean;
import com.fcn.park.property.bean.PropertyParkPayTypeBean;
import java.util.List;
import java.util.Map;

/**
 * Created by 860115032 on 2018/04/08.
 */

public interface PropertyParkPayContract {
    interface View extends BaseView {
        /**
         * 申请类型选择
         * @return
         */
        PropertyParkPayTypeBean getRadioSelected();

        /**
         * 初始化数据设置
         * @param typeList
         */
        void setInitData(List<PropertyParkPayTypeBean> typeList);

        /**
         * 获取页面提交数据
         * @return
         */
        PropertyParkPayBean getBean();

        /**
         * 获取图片文件
         * @return
         */
        Map<String,String> getImageMap();
        /**
         * 打开在职证明选择图片的PopWindow
         */
        void openSelectPhotoPop1(android.view.View view);

        /**
         * 打开驾驶证选择图片的PopWindow
         */
        void openSelectPhotoPop2(android.view.View view);

        /**
         * 打开行驶证选择图片的PopWindow
         */
        void openSelectPhotoPop3(android.view.View view);

        /**
         * 时间选择
         * @param inputContent
         */
        void itemDataChangeSet(String inputContent);
    }

    interface Presenter {

        /**
         * 获取初始化数据
         */
        void getInitData();

        /**
         * 提交申请
         */
        void onClickApplyCommit();

        /**
         * 时间选择
         * @param inputContent
         */
        void itemDataChange(String inputContent);
    }
}
