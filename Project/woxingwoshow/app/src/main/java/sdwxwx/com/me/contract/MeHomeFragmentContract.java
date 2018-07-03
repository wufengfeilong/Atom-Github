package sdwxwx.com.me.contract;

import com.liaoinstan.springview.widget.SpringView;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.databinding.MeHomeVideoBinding;
import sdwxwx.com.home.bean.PlayVideoBean;

/**
 * Created by 860117073 on 2018/5/11.
 */

public interface MeHomeFragmentContract {
    interface View extends BaseView,RecyclerViewContract.View<PlayVideoBean>,SpringView.OnFreshListener{
       SpringView getSpringView();
    }

    interface Presenter {
        void loadMeVideoData(String page,MeHomeVideoBinding mDataBinding);
        void loadUpVideoData(String page,MeHomeVideoBinding mDataBinding);
    }
}
