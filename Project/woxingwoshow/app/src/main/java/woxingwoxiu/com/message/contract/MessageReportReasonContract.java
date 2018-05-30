package woxingwoxiu.com.message.contract;

import woxingwoxiu.com.base.BaseView;

/**
 * Created by 860117066 on 2018/05/15.
 * 类描述：好友举报原因
 */

public interface MessageReportReasonContract {
    interface View extends BaseView {

    }
    interface Presenter {
        void onSummit();
    }
}
