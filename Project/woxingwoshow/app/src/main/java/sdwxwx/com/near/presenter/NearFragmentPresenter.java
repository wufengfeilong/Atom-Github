package sdwxwx.com.near.presenter;

import android.content.Intent;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.near.contract.NearFragmentContract;
import sdwxwx.com.near.model.NearFragmentModel;

import java.util.List;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:51
 */
public class NearFragmentPresenter extends BasePresenter<NearFragmentContract.View> implements NearFragmentContract.Presenter {

    @Override
    public void loadListData(double longitude, double latitude, String page) {

        NearFragmentModel.getVideoNearBy(LoginHelper.getInstance().getUserId(), LoginHelper.getInstance().getCityId(), String.valueOf(longitude), String.valueOf(latitude), page, new BaseCallback<List<PlayVideoBean>>() {
            @Override
            public void onSuccess(List<PlayVideoBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().bindListData(data);
            }

            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().hideLoading();
                getView().getSpringView().onFinishFreshAndLoad();
                Intent intent = new Intent("com.sdwxwx.load.video.list.end");
                getView().getContext().sendBroadcast(intent);
            }
        });


    }

}
