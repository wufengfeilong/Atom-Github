package sdwxwx.com.me.contract;

import sdwxwx.com.base.BaseView;

/**
 * Created by 860115025 on 2018/05/11.
 */

public class QRCodeGenerateContract  {
    public interface View extends BaseView {
        void shareQrCode();
    }

    /**
     * 定义画面的操作事件
     */
    public interface Presenter{
        // 点击返回按钮
        void onBack();
        // 点击分享按钮
        void onShare();
    }
}
