package com.fcn.park.manager.contract;

import com.fcn.park.base.BaseView;
import com.fcn.park.manager.bean.ManagerMessageReplyDetailInfoBean;


/**
 * Created by 丁胜胜 on 2018/04/25.
 * 类描述：管理中心留言详情用Contract
 */

public interface ManagerMessageReplyDetailContract {

    interface View extends BaseView {
        void updateInfo(ManagerMessageReplyDetailInfoBean bean);
        // 接口：获取留言id
        String getSuggestionId();
        // 接口：获取输入的回复内容
        String getInputReplyData();
}

    interface Presenter {
        void loadInfo();
        // 接口：点击按钮的click
        void replyOnClick();

    }
}
