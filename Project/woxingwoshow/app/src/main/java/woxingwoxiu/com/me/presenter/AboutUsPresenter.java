package woxingwoxiu.com.me.presenter;


import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.me.contract.AboutUsContract;

/**
 * Created by 860117073 on 2018/5/9.
 */

public class AboutUsPresenter extends BasePresenter<AboutUsContract.View> implements AboutUsContract.Presenter {
    // 点击返回按钮
    @Override
    public void onClickBack() {
        getView().closeActivity();
    }
}
