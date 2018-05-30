package woxingwoxiu.com.home.presenter;

import woxingwoxiu.com.base.BaseCallback;
import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.home.bean.BannerBean;
import woxingwoxiu.com.home.bean.PlayVideoBean;
import woxingwoxiu.com.home.contract.HomeCategoryVideoContract;
import woxingwoxiu.com.home.model.HomeCategoryVideoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:51
 */
public class HomeCategoryVideoPresenter extends BasePresenter<HomeCategoryVideoContract.View> implements HomeCategoryVideoContract.Presenter{
    HomeCategoryVideoModel mModel;

    @Override
    public void attachView(HomeCategoryVideoContract.View mvpView) {
        super.attachView(mvpView);
        mModel = new HomeCategoryVideoModel();
    }

    @Override
    public void loadListData(String category_id,String city_id,String page) {
        List<PlayVideoBean> list = new ArrayList<>();
        PlayVideoBean bean = new PlayVideoBean();
        bean.setCreate_time("2018-04-05");
        bean.setNickname("dada");
        bean.setTitle("乞丐版的自我救赎1");
        bean.setLike_count(1234);
        list.add(bean);
        PlayVideoBean bean1 = new PlayVideoBean();
        bean1.setCreate_time("2018-04-05");
        bean1.setNickname("dada");
        bean1.setTitle("乞丐版的自我救赎2乞丐版的自我救赎2乞丐版的自我救赎2");
        bean1.setLike_count(1234);
        list.add(bean1);
        PlayVideoBean bean2 = new PlayVideoBean();
        bean2.setCreate_time("2018-04-05");
        bean2.setNickname("dada");
        bean2.setTitle("乞丐版的自我救赎3");
        bean2.setLike_count(1234);
        list.add(bean2);
        PlayVideoBean bean3 = new PlayVideoBean();
        bean3.setCreate_time("2018-04-05");
        bean3.setNickname("dada");
        bean3.setTitle("乞丐版的自我救赎4");
        bean3.setLike_count(1234);
        list.add(bean3);
        getView().bindListData(list);
        mModel.getVideoList("0", category_id, "0", city_id, page, new BaseCallback<List<PlayVideoBean>>() {
            @Override
            public void onSuccess(List<PlayVideoBean> data) {
                getView().bindListData(data);
            }

            @Override
            public void onFail(String msg) {
                getView().showToast(msg);
            }
        });


    }

    @Override
    public void loadBannerData() {
        mModel.getBanners(new BaseCallback<List<BannerBean>>() {
            @Override
            public void onSuccess(List<BannerBean> data) {
                getView().bindBannerData(data);
            }

            @Override
            public void onFail(String msg) {
                getView().showToast(msg);
            }
        });
    }

    @Override
    public void loadListData() {

    }
}
