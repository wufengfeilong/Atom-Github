package sdwxwx.com.me.presenter;

import android.view.View;

import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.me.activity.AboutUsActivity;
import sdwxwx.com.me.contract.QRCodeGenerateContract;

/**
 * Created by 860115025 on 2018/05/11.
 */

public class QRCodeGeneratePresenter extends BasePresenter<QRCodeGenerateContract.View> implements QRCodeGenerateContract.Presenter {

    /**
     * 返回前一画面
     */
    @Override
    public void onBack() {
        // 关闭当前的Activity，返回上一个画面
        getView().closeActivity();
    }

    /**
     *跳转到分享画面
     */
    @Override
    public void onShare() {
        getView().shareQrCode();
    }
}
