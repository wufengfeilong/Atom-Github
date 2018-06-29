package com.fcn.park.me.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.me.bean.MeMsgDetailBean;

/**
 * create by 860115039
 * date      2018/04/23
 * time      13:06
 * 个人中心-消息详情Contract
 */
public interface MeMyMsgDetailContract {
    interface View extends BaseView{
        /**
         * 获取消息Id
         */
        String getMsgId();
        /**
         * 获取消息详情
         */
        void setMsgDetailInfo(MeMsgDetailBean bean);
        /**
         * 带参数跳转画面
         */
        void jumpActivity(Class cl,String type,String parkPayId);

        /**
         * 带参数跳转画面,2个参数
         */
        void jumpActivity(Class cl,String flg);
    }

    interface Presenter {
        /**
         * 加载消息详细
         */
        void loadMsgDetailById();
        /**
         * 去缴费
         */
        void toPayFee();
    }
}
