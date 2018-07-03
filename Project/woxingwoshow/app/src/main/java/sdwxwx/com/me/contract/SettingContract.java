package sdwxwx.com.me.contract;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.me.bean.UpdateInfoBean;

/**
 * Created by 860117073 on 2018/5/11.
 */

public interface SettingContract {
    interface View extends BaseView {
        void refresh(int type);
        /**
         * 可选择是否立即更新
         */
        void showSelectUpdateDialog(UpdateInfoBean bean);
        /**
         * 已经是最新版本，无需更新
         */
        void showNoNeedUpdateDialog();
    }

    interface Presenter {
        // 点击认证信息
        void onClickCertify();

        void onClickBack();

        // 点击我的二维码
        void onClickQrCode();

        // 清除缓存
        void onClickClearCaches();

        // 点击用户反馈
        void onClickFeedback();

        // 点击关于我们
        void onClickAboutUs();

        // 点击退出登录
        void onClicklogOut();

        // 点击检查更新
        void onClickCheckUpdate();
        // APK下载
        void versionUpdate(UpdateInfoBean bean);

    }
}
