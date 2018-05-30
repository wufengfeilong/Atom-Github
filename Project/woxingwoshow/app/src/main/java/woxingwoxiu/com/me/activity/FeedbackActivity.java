package woxingwoxiu.com.me.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;
import woxingwoxiu.com.databinding.ActivityFeedbackBinding;
import woxingwoxiu.com.me.contract.FeedbackContract;
import woxingwoxiu.com.me.presenter.FeedbackPresenter;

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
}
