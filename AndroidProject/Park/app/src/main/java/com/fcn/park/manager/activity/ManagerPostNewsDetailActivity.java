package com.fcn.park.manager.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;

import com.bumptech.glide.Glide;
import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.http.ApiService;
import com.fcn.park.databinding.ManagerPostNewsDetailBinding;
import com.fcn.park.manager.bean.DetailInfoBean;
import com.fcn.park.manager.contract.ManagerPostNewsDetailContract;
import com.fcn.park.manager.presenter.ManagerPostNewsDetailPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 新闻、公告、活动详情画面用Activity.
 */
public class ManagerPostNewsDetailActivity
        extends BaseActivity<ManagerPostNewsDetailBinding, ManagerPostNewsDetailContract.View, ManagerPostNewsDetailPresenter>
        implements ManagerPostNewsDetailContract.View{

    private String TAG = "=== ManagerPostNewsDetailActivity ===";

    // 新闻、公告、活动的Id
    private String mId;

    // 类型（0：公告公示 1：园区新闻 2：园区活动）
    private String mNewsType;

    String newsTitle = null;
    String newsSources = null;
    String newsContent = null;
    String newsThumbnail = null;

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        if (mNewsType.equals(Constant.NEWS_TYPE_0)) { // 公告公示
            setTitle(getString(R.string.manager_make_announcement_detail));
        } else if (mNewsType.equals(Constant.NEWS_TYPE_1)) { // 园区新闻
            setTitle(getString(R.string.manager_post_news_detail));
        } else if (mNewsType.equals(Constant.NEWS_TYPE_2)) { // 园区活动
            setTitle(getString(R.string.manager_publish_activities_detail));
        }
    }

    private void setTitle(String titleText) {
        setTitleText(titleText);
        openTitleLeftView(true);
        mLayoutTitleRight.setVisibility(View.VISIBLE);
        mTvTitleRight.setText(R.string.manager_edit_title);
        mLayoutTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(Constant.NEWS_TYPE, mNewsType);
                intent.putExtra("newsId", mId);
                intent.putExtra("newsTitle", newsTitle);
                intent.putExtra("newsSources", newsSources);
                intent.putExtra("newsThumbnail", newsThumbnail);
                intent.putExtra("newsContent", newsContent);
                intent.setClass(getContext(), ManagerPostNewsDetailEditActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    /**
     * 重写的方法，用来加载定义画面的Layout
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.manager_post_news_detail;
    }

    public static void actionStart(Context context, String newsType, String id) {
        Intent intent = new Intent(context, ManagerPostNewsDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra(Constant.NEWS_TYPE, newsType);
        context.startActivity(intent);
    }

    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mNewsType = intent.getStringExtra(Constant.NEWS_TYPE);
    }

    @Override
    protected void initViews() {

        mPresenter.loadInfo();
        WebSettings webSettings = mDataBinding.webView.getSettings();
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public String getNewsType() {
        return mNewsType;
    }

    @Override
    protected ManagerPostNewsDetailPresenter createPresenter() {
        return new ManagerPostNewsDetailPresenter();
    }

    /**
     * 将从后台取到的新闻、公告、活动的内容显示到画面上
     * @param bean
     */
    @SuppressLint("JavascriptInterface")
    @Override
    public void updateInfo(DetailInfoBean bean) {
        mDataBinding.setInfoBean(bean);
        newsTitle = bean.getNewsTitle();
        newsSources = bean.getNewsSources();
        newsContent = bean.getNewsContent();
        newsThumbnail = bean.getNewsThumbnail();
        if (newsThumbnail != null) {
            Glide.with(this)
                    .load(ApiService.IMAGE_BASE + newsThumbnail)
                    .error(R.drawable.ic_vector_default_none_img)
                    .into(mDataBinding.managerPostNewsDetailThumbnail);
        }
        mDataBinding.webView.loadDataWithBaseURL("", getNewsContent(bean.getNewsContent()), "text/html", "utf-8", "");
    }



    private String getNewsContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.parent().attr("style","text-align:center");
            element.attr("style", "max-width:90%;height:auto;");
        }
        Log.d(TAG, "====== doc = " + doc.toString());
        return doc.toString();
    }
}