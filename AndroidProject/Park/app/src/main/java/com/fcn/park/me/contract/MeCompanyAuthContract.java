package com.fcn.park.me.contract;

import com.fcn.park.base.BaseView;

/**
 * create by 860115039
 * date      2018/04/12
 * time      9:04
 * 企业认证Contract
 */
public interface MeCompanyAuthContract {
    interface View extends BaseView {
        /**
         * 公司名
         */
        String getCompanyName();
        /**
         * 公司详细地址
         */
        String getDetailAddr();
        /**
         * 公司联系电话
         */
        String getLandLineNum();
        /**
         * 公司联系人
         */
        String getContactPerson();
        /**
         * 公司邮箱地址
         */
        String getEmailAddr();
        /**
         * 公司所属行业
         */
        String getIndustryType();
        /**
         * 公司营业执照
         */
        String getLicenseImgUrl();
        /**
         * 公司logo
         */
        String getLogoImgUrl();
        /**
         * 公司认证flg
         */
        String getFlg();
        /**
         * 打开营业执照选择pop
         */
        void openSelectLicensePhotoPop();
        /**
         * 打开公司logo选择pop
         */
        void openSelectLogoPhotoPop();

        /**
         * 返回保存成功的消息
         */
        void saveOk();
    }

    interface Presenter {
        /**
         * 添加公司营业执照
         */
        void addLicense();
        /**
         * 添加公司logo
         */
        void addLogo();
        /**
         * 点击保存
         */
        void onClickSave();
    }
}
