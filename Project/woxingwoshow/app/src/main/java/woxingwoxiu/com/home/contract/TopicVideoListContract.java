package woxingwoxiu.com.home.contract;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.home.bean.TopicVideoBean;

/**
 * Created by 860117073 on 2018/5/29.
 */

public interface TopicVideoListContract {

    interface View extends BaseView,RecyclerViewContract.View<TopicVideoBean>  {
        void finishThis();
    }

    interface Presenter extends RecyclerViewContract.Presenter{
    }
}
