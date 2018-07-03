package sdwxwx.com.home;

import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.release.activity.RecordActivity;

/**
 * create by 860115039
 * date      2018/5/8
 * time      16:12
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {


    @Override
    public void toHome() {
//        getView().setShowFragment(0);
        getView().switchContent(0);
    }

    @Override
    public void toNear() {
//        getView().setShowFragment(1);
        getView().switchContent(1);
    }

    @Override
    public void toCapture() {
        getView().actionStartActivity(RecordActivity.class);
    }

    @Override
    public void toMsg() {
//        getView().setShowFragment(2);
        getView().switchContent(2);
    }

    @Override
    public void toMe() {
//        getView().setShowFragment(3);
        getView().switchContent(3);
    }
}
