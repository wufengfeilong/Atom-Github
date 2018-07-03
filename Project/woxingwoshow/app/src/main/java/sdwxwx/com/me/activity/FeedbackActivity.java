package sdwxwx.com.me.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import sdwxwx.com.R;
import sdwxwx.com.base.BaseActivity;
import sdwxwx.com.databinding.ActivityFeedbackBinding;
import sdwxwx.com.me.contract.FeedbackContract;
import sdwxwx.com.me.presenter.FeedbackPresenter;
import sdwxwx.com.widget.ToastUtil;

import java.util.List;

public class FeedbackActivity extends BaseActivity<ActivityFeedbackBinding, FeedbackPresenter> implements FeedbackContract.View {

    @Override
    protected FeedbackPresenter createPresenter() {
        return new FeedbackPresenter();
    }

    @Override
    protected void initViews() {
        mDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public String getContent() {
        return mDataBinding.feedbackContent.getText().toString().trim();
    }

    /**
     * 退出画面的时候让toast消失
     */
    @Override
    protected void onPause() {
        super.onPause();
        ToastUtil.cancelToast();
    }
}
