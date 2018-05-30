package woxingwoxiu.com.me.contract;

import woxingwoxiu.com.base.BaseView;

/**
 * Created by 860115025 on 2018/05/15.
 */

public class QRCodeScanContract {
    public interface View extends BaseView {

    }

    /**
     * 定义画面的操作事件
     */
    public interface Presenter{
        // 点击返回按钮
        void onBack();
        // 查看我的二维码
        void onShowQrCode();
    }
}
