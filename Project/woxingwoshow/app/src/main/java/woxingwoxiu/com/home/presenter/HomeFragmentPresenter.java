package woxingwoxiu.com.home.presenter;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.home.activity.PickCityActivity;
import woxingwoxiu.com.home.activity.SearchUserActivity;
import woxingwoxiu.com.home.contract.HomeFragmentContract;
import woxingwoxiu.com.letter.activity.LetterListActivity;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:51
 */
public class HomeFragmentPresenter extends BasePresenter<HomeFragmentContract.View> implements HomeFragmentContract.Presenter{

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
