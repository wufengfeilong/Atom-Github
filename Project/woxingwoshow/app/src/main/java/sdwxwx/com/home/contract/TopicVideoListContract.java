package sdwxwx.com.home.contract;

import com.liaoinstan.springview.widget.SpringView;
import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.home.bean.PlayVideoBean;

/**
 * Created by 860117073 on 2018/5/29.
 */

public interface TopicVideoListContract {

    interface View extends BaseView, RecyclerViewContract.View<PlayVideoBean>,SpringView.OnFreshListener {

        String getTopicId();

        void finishThis();

        SpringView getSpringView();

    }

    interface Presenter extends RecyclerViewContract.Presenter {
        void loadListData(String page);
    }
}
