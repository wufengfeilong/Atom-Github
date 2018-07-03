package sdwxwx.com.home.presenter;

import android.content.Intent;
import android.util.Log;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.bean.CategoryBean;
import sdwxwx.com.home.bean.BannerBean;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.home.contract.HomeCategoryVideoContract;
import sdwxwx.com.home.model.HomeCategoryVideoModel;
import sdwxwx.com.home.model.HomeFragmentModel;
import sdwxwx.com.login.utils.LoginHelper;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:51
 */
public class HomeCategoryVideoPresenter extends BasePresenter<HomeCategoryVideoContract.View> implements HomeCategoryVideoContract.Presenter {
    HomeCategoryVideoModel mModel;
    private static final String TAG = "HomeCategoryVideo";

    @Override
    public void attachView(HomeCategoryVideoContract.View mvpView) {
        super.attachView(mvpView);
        mModel = new HomeCategoryVideoModel();
    }

    @Override
    public void loadListData(String category_id, String page) {
//        getView().showLoading();
        mModel.getVideoList(LoginHelper.getInstance().getUserId(), "0", category_id, "0", "0", page, new BaseCallback<List<PlayVideoBean>>() {
            @Override
            public void onSuccess(List<PlayVideoBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().bindListData(data);
//                getView().hideLoading();
            }

            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast(msg);
                getView().getSpringView().onFinishFreshAndLoad();
                Intent intent = new Intent("com.sdwxwx.load.video.list.end");
                getView().getContext().sendBroadcast(intent);


            }
        });


    }

    @Override
    public void loadBannerData() {
        mModel.getBanners(new BaseCallback<List<BannerBean>>() {
            @Override
            public void onSuccess(List<BannerBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().bindBannerData(data);
            }

            @Override
            public void onFail(String msg) {
                Log.d(TAG, "onFail: " + msg);
            }
        });
    }

    @Override
    public void loadCategoryData() {
        new HomeFragmentModel().getCategories(new BaseCallback<List<CategoryBean>>() {
            @Override
            public void onSuccess(List<CategoryBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().bindCategoryData(data);
            }

            @Override
            public void onFail(String msg) {
                if (getView() != null) {
                    getView().showToast(msg);
                }
            }
        });
    }

    @Override
    public void loadListData() {

    }
}
