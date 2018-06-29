package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.manager.bean.ManagerPostNewsListBean;
import com.fcn.park.manager.contract.ManagerPostNewsAddContract;
import com.fcn.park.manager.module.ManagerPostNewsAddModule;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * 管理中心的新闻、公告、活动发布功能用Presenter.
 */
public class ManagerPostNewsAddPresenter extends BasePresenter<ManagerPostNewsAddContract.View> implements ManagerPostNewsAddContract.Presenter {

    private String TAG = "=== ManagerPostNewsPresenter ===";
    private ManagerPostNewsAddModule mManagerPostNewsAddModule;

    @Override
    public void attach(ManagerPostNewsAddContract.View view) {
        super.attach(view);
        mManagerPostNewsAddModule = new ManagerPostNewsAddModule();
    }

    /**
     * 发布新闻的“发布”按钮的点击事件
     */
    @Override
    public void onClickNewsPublish() {

        if (getView().checkInputNewsTitleEmpty()) {
            getView().showToast("请输入主标题");
            return;
        }

        if (getView().checkInputNewsSourcesEmpty()) {
            getView().showToast("请输入来源");
            return;
        }

        if (getView().checkInputNewsContentEmpty()) {
            getView().showToast("请输入内容");
            return;
        }

        // 获取画面Activity的数据Bean的对象
        final ManagerPostNewsListBean.ListNewsBean newsBean = getView().getNewsBean();
        // 获取画面Activity的缩略图的对象
        final Map<String, String> newsThumbnailMap = getView().getNewsThumbnail();

        // 将画面上输入的内容保存到数据Bean中
        newsBean.setNewsTitle(getView().getInputNewsTitle());
        newsBean.setCategory(getView().getNewType());
        newsBean.setNewsSources(getView().getInputNewsSources());
        newsBean.setNewsContent(getView().getInputNewsContent());
        newsBean.setInsertUser(LoginHelper.getInstance().getUserToken());
        newsBean.setUpdateUser(LoginHelper.getInstance().getUserToken());

        Map<String, String> reqNewsBeanMap = ApiClient.createBodyMap(newsBean);
        MultipartBody.Part newsThumbnail = ApiClient.getFileBody("newsThumbnail", new File(newsThumbnailMap.get("newsThumbnail")));

        mManagerPostNewsAddModule.addPostNewsInfo(getView().getContext(), reqNewsBeanMap, newsThumbnail, new OnDataCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().showToast("发布成功");
                getView().closeActivity();
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast("发布失败");
            }
        });
    }
}
