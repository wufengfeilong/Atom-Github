package com.fcn.park.manager.presenter;

import com.fcn.park.base.BasePresenter;
import com.fcn.park.base.http.ApiClient;
import com.fcn.park.base.interfacee.OnDataCallback;
import com.fcn.park.login.LoginHelper;
import com.fcn.park.manager.bean.ManagerPostNewsListBean;
import com.fcn.park.manager.contract.ManagerPostNewsDetailEditContract;
import com.fcn.park.manager.module.ManagerPostNewsDetailEditModule;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * 管理中心的新闻、公告、活动编辑功能用Presenter.
 */

public class ManagerPostNewsDetailEditPresenter extends BasePresenter<ManagerPostNewsDetailEditContract.View> implements ManagerPostNewsDetailEditContract.Presenter {

    private String TAG = "=== ManagerPostNewsPresenter ===";
    private ManagerPostNewsDetailEditModule mManagerPostNewsDetailEditModule;

    @Override
    public void attach(ManagerPostNewsDetailEditContract.View view) {
        super.attach(view);
        mManagerPostNewsDetailEditModule = new ManagerPostNewsDetailEditModule();
    }

    @Override
    public void onClickNewsDetailEdit() {

        if (getView().checkInputNewsTitleEmpty()) {
            getView().showToast("请输入新闻主标题");
            return;
        }

        if (getView().checkInputNewsSourcesEmpty()) {
            getView().showToast("请输入新闻来源");
            return;
        }

        if (getView().checkInputNewsContentEmpty()) {
            getView().showToast("请输入新闻内容");
            return;
        }

        // 获取画面Activity的数据Bean的对象
        final ManagerPostNewsListBean.ListNewsBean newsDetailBean = getView().getNewsDetailBean();
        // 获取画面Activity的缩略图的对象
        final Map<String, String> newsDetailThumbnailMap = getView().getNewsDetailThumbnail();

        // 将画面上输入的内容保存到数据Bean中
        newsDetailBean.setNewsTitle(getView().getInputNewsTitle());
        newsDetailBean.setNewsSources(getView().getInputNewsSources());
        newsDetailBean.setNewsContent(getView().getInputNewsContent());
        newsDetailBean.setUpdateUser(LoginHelper.getInstance().getUserToken());

        Map<String, String> reqNewsBeanMap = ApiClient.createBodyMap(newsDetailBean);
        MultipartBody.Part newsThumbnail = ApiClient.getFileBody("newsThumbnail", new File(newsDetailThumbnailMap.get("newsThumbnail")));

        mManagerPostNewsDetailEditModule.updateNewsDetailInfo(getView().getContext(), reqNewsBeanMap, newsThumbnail, new OnDataCallback<String>() {
            @Override
            public void onSuccessResult(String data) {
                getView().showToast("更新成功");
                getView().closeActivity();
            }

            @Override
            public void onError(String errMsg) {
                getView().showToast("发布失败");
            }
        });
    }
}