package sdwxwx.com.home.presenter;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.bean.CategoryBean;
import sdwxwx.com.home.activity.PickCityActivity;
import sdwxwx.com.home.activity.SearchUserActivity;
import sdwxwx.com.home.contract.HomeFragmentContract;
import sdwxwx.com.home.model.HomeFragmentModel;
import sdwxwx.com.letter.activity.LetterListActivity;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:51
 */
public class HomeFragmentPresenter extends BasePresenter<HomeFragmentContract.View> implements HomeFragmentContract.Presenter {
    HomeFragmentModel mModel;

    @Override
    public void attachView(HomeFragmentContract.View mvpView) {
        super.attachView(mvpView);
        mModel = new HomeFragmentModel();
    }

    @Override
    public void loadTabData() {
        mModel.getCategories(new BaseCallback<List<CategoryBean>>() {
            @Override
            public void onSuccess(List<CategoryBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().loadTabs(data);
            }

            @Override
            public void onFail(String msg) {
                if (getView()!=null) {
                    getView().showToast(msg);
                }
            }
        });
    }

    @Override
    public void loadListData() {

    }

    @Override
    public void backClick() {
        getView().hideCategoryVideo(true);
    }

    @Override
    public void tabMoreClick() {
        getView().showCategoryVideo();
    }

    @Override
    public void cityClick() {
        getView().actionStartActivity(PickCityActivity.class);
    }

    @Override
    public void searchClick() {
        getView().actionStartActivity(SearchUserActivity.class);
    }

    @Override
    public void letterClick() {
        getView().actionStartActivity(LetterListActivity.class);
    }

}
