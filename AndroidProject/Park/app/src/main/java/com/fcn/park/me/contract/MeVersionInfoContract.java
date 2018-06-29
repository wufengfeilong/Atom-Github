package com.fcn.park.me.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.me.bean.VersionInfoBean;

/**
 * create by 860115039
 * date      2018/04/23
 * time      13:06
 * 个人中心-版本更新
 */
public interface MeVersionInfoContract {
    interface View extends BaseView{
        int getVersionCode();
        String getVersionName();
        /**
         * 已经是最新版本，无需更新
         */
        void showNoNeedUpdateDialog();
        /**
         * 可选择是否立即更新
         */
        void showSelectUpdateDialog(VersionInfoBean bean);
        /**
         * 必须更新
         */
        void showNeedUpdateDialog(VersionInfoBean bean);
        /**
         * 设置版本信息
         */
        void setVersionInfo(String code);
    }

    interface Presenter {
        /**
         * 检测新版本
         */
        void versionDetect();
        /**
         * 进行更新
         */
        void versionUpdate(VersionInfoBean bean);
    }
}
