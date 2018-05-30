package woxingwoxiu.com.message.activity;
/**
 * Created by 860117066 on 2018/05/15.
 * 类描述：好友举报原因
 */

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.MessageReportReasonActivityBinding;
import woxingwoxiu.com.message.contract.MessageReportReasonContract;
import woxingwoxiu.com.message.presenter.MessageReportReasonPresenter;

public class MessageReportReasonActivity extends BaseActivity<MessageReportReasonActivityBinding,MessageReportReasonPresenter>
        implements MessageReportReasonContract.View{
    //页面初始化
    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_report_reason_activity;
    }

    @Override
    protected MessageReportReasonPresenter createPresenter() {
        return new MessageReportReasonPresenter();
    }

}
