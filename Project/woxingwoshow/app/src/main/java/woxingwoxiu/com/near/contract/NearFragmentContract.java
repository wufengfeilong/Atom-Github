package woxingwoxiu.com.near.contract;

import com.liaoinstan.springview.widget.SpringView;
import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.home.bean.CategoryVideoListBean;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:47
 */
public interface NearFragmentContract {
    interface View extends BaseView,RecyclerViewContract.View<CategoryVideoListBean>,SpringView.OnFreshListener {

    }

    interface Presenter extends RecyclerViewContract.Presenter{

        void loadListData();
    }
}
