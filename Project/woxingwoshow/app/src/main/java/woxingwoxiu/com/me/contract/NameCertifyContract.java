package woxingwoxiu.com.me.contract;

import woxingwoxiu.com.base.BaseView;

/**
 * Created by 860117073 on 2018/5/11.
 */

public interface NameCertifyContract {
    interface View extends BaseView {

    }

    interface Presenter {
        // 点击返回按钮
        void onClickBack();
    }
}
