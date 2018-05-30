package woxingwoxiu.com.me.contract;

import woxingwoxiu.com.base.BaseView;

/**
 * Created by 860117073 on 2018/5/11.
 */

public interface FeedbackContract {
    interface View extends BaseView {
        String getContent();

    }

    interface Presenter {
       void getData();
    }
}
