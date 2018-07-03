package sdwxwx.com.me.contract;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.me.bean.AboutBean;

/**
 * Created by 860117073 on 2018/5/11.
 */

public interface AboutUsContract {
    interface View extends BaseView {
        void  setAboutUsBean(AboutBean bean);

    }

    interface Presenter {
        // 画面初期化
        AboutBean initView();
        // 点击返回
        void onClickBack();
    }
}
