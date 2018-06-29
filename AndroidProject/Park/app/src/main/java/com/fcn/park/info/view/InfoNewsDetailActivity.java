package com.fcn.park.info.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.InfoNewsDetailBinding;
import com.fcn.park.info.bean.InfoNewsBean;
import com.fcn.park.info.contract.InfoNewsDetailContract;
import com.fcn.park.info.presenter.InfoNewsDetailPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by liuyq on 2018/04/12.
 * 公告活动新闻详情页面
 */

public class InfoNewsDetailActivity extends BaseActivity<InfoNewsDetailBinding, InfoNewsDetailContract.View, InfoNewsDetailPresenter> implements InfoNewsDetailContract.View {

    private static final String ID_TAG = "id";

    private String mId;

    /**
     * @param context
     * @param id
     */
    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, InfoNewsDetailActivity.class);
        intent.putExtra(ID_TAG, id);
        context.startActivity(intent);
    }

    @Override
    protected void setupTitle() {
        setTitleText("详情");
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();
        mId = intent.getStringExtra(ID_TAG);
    }

    @Override
    public String getId() {
        return mId;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initViews() {
        mPresenter.loadInfo();
//        mDataBinding.dashedLine1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        mDataBinding.dashedLine2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        mDataBinding.dashedLine3.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        WebSettings webSettings = mDataBinding.webView.getSettings();
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        // webSettings.setLayoutAlgorithm(LayoutAlgorithn.NARROW_COLUMNS);//适应内容大小
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_news_detail;
    }

    @Override
    protected InfoNewsDetailPresenter createPresenter() {
        return new InfoNewsDetailPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void updateInfo(InfoNewsBean bean) {
        mDataBinding.setInfoBean(bean);
        mDataBinding.webView.loadDataWithBaseURL("", getInfoNewsContent(bean.getContent()), "text/html", "utf-8", "");
    }

    private String getInfoNewsContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            //element.attr("width", "80%").attr("height", "auto");
            element.parent().attr("style","text-align:center");
            element.attr("style", "max-width:90%;height:auto;");
        }
        return doc.toString();
    }

}
