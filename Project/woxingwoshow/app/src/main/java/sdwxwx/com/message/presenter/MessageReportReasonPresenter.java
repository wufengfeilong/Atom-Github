package sdwxwx.com.message.presenter;

import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.message.contract.MessageReportReasonContract;

/**
 * Created by 860117066 on 2018/05/15.
 * 类描述：好友举报原因
 */

public class MessageReportReasonPresenter extends BasePresenter<MessageReportReasonContract.View> implements MessageReportReasonContract.Presenter{

    @Override
    public void onSummit() {
        getView().showToast("123");
    }
}
