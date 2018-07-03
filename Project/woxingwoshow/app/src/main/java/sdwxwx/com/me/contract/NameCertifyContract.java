package sdwxwx.com.me.contract;

import sdwxwx.com.base.BaseView;

/**
 * Created by 860117073 on 2018/5/11.
 */

public interface NameCertifyContract {
    interface View extends BaseView {
        void selectPhoto(int type);
        void certificate();
    }

    interface Presenter {
        // 点击返回按钮
        void onClickBack();
        // 点击上传手持身份证照片
        void onClickHandPhoto();
        // 点击上传独立身份证照片
        void onClickCertyPhoto();
        // 点击进行实名认证
        void onClickSave();
        // 用户协议
        void onClickGoUserAgreement();
    }
}
