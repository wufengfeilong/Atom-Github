package woxingwoxiu.com.me.presenter;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.me.activity.AboutUsActivity;
import woxingwoxiu.com.me.contract.QRCodeGenerateContract;

/**
 * Created by 860115025 on 2018/05/11.
 */

public class QRCodeGeneratePresenter extends BasePresenter<QRCodeGenerateContract.View> implements QRCodeGenerateContract.Presenter {

    /**
     * 返回前一画面
     */
    @Override
    public void onBack() {
        getView().showToast("返回前一画面");
        // 关闭当前的Activity，返回上一个画面
        getView().closeActivity();
    }

    /**
     *跳转到分享画面
     */
    @Override
    public void onShare() {
        getView().showToast("跳转到分享画面");
        // TODO 分享画面完成后，修改为跳转到分享画面
        getView().actionStartActivity(AboutUsActivity.class);
    }
}
