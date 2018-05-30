package woxingwoxiu.com.me.presenter;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.login.activity.LoginPhoneActivity;
import woxingwoxiu.com.me.contract.FeedbackContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 860117073 on 2018/5/9.
 */

public class FeedbackPresenter extends BasePresenter<FeedbackContract.View> implements FeedbackContract.Presenter {

    public void getData() {
        String content= getView().getContent();
        getView().showToast("反馈成功！");
        getView().closeActivity();
    }

    /**
     * 返回前一画面
     */
    public void onBack() {
        // 关闭当前的Activity，返回上一个画面
        getView().closeActivity();
    }
}
