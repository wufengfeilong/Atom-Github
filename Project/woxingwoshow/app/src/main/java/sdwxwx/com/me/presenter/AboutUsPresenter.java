package sdwxwx.com.me.presenter;


import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.me.bean.AboutBean;
import sdwxwx.com.me.contract.AboutUsContract;
import sdwxwx.com.me.model.AboutUsModel;

import java.util.List;

/**
 * Created by 860117073 on 2018/5/9.
 */

public class AboutUsPresenter extends BasePresenter<AboutUsContract.View> implements AboutUsContract.Presenter {
    /** 关于我们 */
    private AboutUsModel mModel;
    private AboutBean mBean;

    @Override
    public void attachView(AboutUsContract.View mvpView) {
        super.attachView(mvpView);
        mModel = new AboutUsModel();
    }
    // 画面初期化
    @Override
    public AboutBean initView() {
        /*// 调用网络接口进行更新数据
        mModel.getAgreement(new BaseCallback<List<AgreementBean>>() {
                    @Override
                    public void onSuccess(List<AgreementBean> data) {
                        data.get(0);
                        getView().showToast("取得成功");
                        getView().closeActivity();
                    }
                    @Override
                    public void onFail(String msg) {
                        getView().showToast("取得失败");
                    }
                });*/
        mModel.getAboutUs(new BaseCallback<List<AboutBean>>() {
            @Override
            public void onSuccess(List<AboutBean> data) {
                mBean = data.get(0);
                if (getView() == null) {
                    return;
                }
                getView().setAboutUsBean(mBean);
            }

            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast(msg);
            }
        });
        return mBean;
    }

    // 点击返回按钮
    @Override
    public void onClickBack() {
        getView().closeActivity();
    }
}
