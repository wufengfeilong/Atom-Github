package woxingwoxiu.com.login.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import woxingwoxiu.com.R;
import woxingwoxiu.com.base.BaseActivity;

import woxingwoxiu.com.databinding.ActivityUserAgreementBinding;
import woxingwoxiu.com.login.bean.UserAgreementBean;
import woxingwoxiu.com.login.contract.UserAgreementContract;
import woxingwoxiu.com.login.presenter.UserAgreementPresenter;
import woxingwoxiu.com.login.utils.ActivityCollector;

/**
 * Created by 丁胜胜 on 2018/05/14.
 * 类描述：用户协议画面
 */
public class UserAgreementActivity extends BaseActivity<ActivityUserAgreementBinding,UserAgreementPresenter>
                implements UserAgreementContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 页面初始化
     */
    @Override
    protected void initViews() {
        mDataBinding.setUserAgreementPresenter(mPresenter);

        ActivityCollector.addActivity(this);//将活动添加到活动收集器
        mDataBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                updateLoadingProgress(newProgress);
            }
        });

        mDataBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showLoadingProgress();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                mDataBinding.loadingProgress.setProgress(100);
                hintLoadingProgress();
            }
        });
        mPresenter.loadPageData();

        ActivityCollector.addActivity(this);//将活动添加到活动收集器
    }

    @Override
    public void bindWebData(UserAgreementBean bean) {
        mDataBinding.webView.loadDataWithBaseURL("", bean.getContent(), "text/html", "utf-8", "");
    }

    @Override
    public void showLoadingProgress() {
        if (mDataBinding.loadingProgress.getVisibility() == View.GONE)
            mDataBinding.loadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hintLoadingProgress() {
        if (mDataBinding.loadingProgress.getVisibility() == View.VISIBLE)
            mDataBinding.loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void updateLoadingProgress(int progress) {
        mDataBinding.loadingProgress.setProgress(progress);
    }

    @Override
    protected UserAgreementPresenter createPresenter() {
        return new UserAgreementPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_agreement;
    }
}
