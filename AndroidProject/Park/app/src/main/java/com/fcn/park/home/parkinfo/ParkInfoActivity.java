package com.fcn.park.home.parkinfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;

import com.fcn.park.R;
import com.fcn.park.base.BaseActivity;
import com.fcn.park.databinding.ParkInfoBinding;
import com.fcn.park.manager.bean.ManagerParkIntroductionBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 园区简介
 */

public class ParkInfoActivity extends BaseActivity<ParkInfoBinding, ParkInfoContract.View, ParkInfoPresenter> implements ParkInfoContract.View {

    private static final String ID_TAG = "id";

    private String mId;

    /**
     * @param context
     * @param id
     */
    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, ParkInfoActivity.class);
        intent.putExtra(ID_TAG, id);
        context.startActivity(intent);
    }

    /**
     * 重写的方法，用来设置标题栏要显示的文字
     */
    @Override
    protected void setupTitle() {
        setTitleText("园区简介");
        openTitleLeftView(true);
    }

    @Override
    protected void initFieldBeforeMethods() {
        super.initFieldBeforeMethods();
        Intent intent = getIntent();
        mId = intent.getStringExtra(ID_TAG);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initViews() {
        SharedPreferences preferences = getSharedPreferences("park_info", Context.MODE_PRIVATE);
        String park_id = preferences.getString("park_id", "");//第二个参数表示如果没有找到，则使用该默认值

        mPresenter.loadInfo(park_id);

        WebSettings webSettings = mDataBinding.webView.getSettings();
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        // webSettings.setLayoutAlgorithm(LayoutAlgorithn.NARROW_COLUMNS);//适应内容大小
    }

    @Override
    protected int getLayoutId() {
        return R.layout.park_info;
    }

    @Override
    protected ParkInfoPresenter createPresenter() {
        return new ParkInfoPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void updateInfo(ManagerParkIntroductionBean bean) {
        if (bean != null) {
            mDataBinding.setBean(bean);
            mDataBinding.webView.loadDataWithBaseURL("", getInfoNewsContent(bean.getParkContent()), "text/html", "utf-8", "");
        }
    }
}
