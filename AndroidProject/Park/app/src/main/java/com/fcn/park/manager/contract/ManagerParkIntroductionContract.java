package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.ManagerParkIntroductionBean;

import java.util.Map;

/**
 * 园区简介用Contract.
 */
public interface ManagerParkIntroductionContract {

    interface View extends BaseView {

        void updateInfo(ManagerParkIntroductionBean bean);

        String getInputParkId();
        String getInputParkName();
        String getInputParkContent();
        String getInputParkAddress();
        String getInputParkTelephone();

        boolean checkInputParkNameEmpty();
        boolean checkInputParkContentEmpty();
        boolean checkInputParkAddressEmpty();
        boolean checkInputParkTelephoneEmpty();

        /**
         * 添加园区简介图片1
         */
        void addParkImg1(android.view.View view);

        /**
         * 添加园区简介图片2
         */
        void addParkImg2(android.view.View view);

        /**
         * 添加园区简介图片3
         */
        void addParkImg3(android.view.View view);

        ManagerParkIntroductionBean getParkBean();

        Map<String,String> getImageMap();

    }

    interface Presenter {
        void onClickPoseParkIntroduction();
        void loadInfo(String parkId);
    }
}
