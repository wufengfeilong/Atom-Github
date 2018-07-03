package sdwxwx.com.login.presenter;

import android.content.Intent;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.home.activity.SearchUserActivity;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.login.activity.LoginActivity;
import sdwxwx.com.login.contract.NotLoginInterfaceContract;
import sdwxwx.com.login.model.NotLoginInterfaceModel;
import sdwxwx.com.login.utils.LoginHelper;

import java.util.List;

/**
 * Created by 丁胜胜 on 2018/05/23.
 */

public class NotLoginInterfacePresenter extends BasePresenter<NotLoginInterfaceContract.View>
                    implements NotLoginInterfaceContract.Presenter{

    NotLoginInterfaceModel mModel;

    @Override
    public void loadListData(String page) {

        getView().showLoading();
        mModel.getVideoList("0", page, new BaseCallback<List<PlayVideoBean>>() {
            @Override
            public void onSuccess(List<PlayVideoBean> data) {
                LoginHelper.getInstance().setPlayVideoList(data);
                if (getView() == null) {
                    return;
                }
                getView().bindListData(data);
                getView().hideLoading();
            }

            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast(msg);
                getView().getSpringView().onFinishFreshAndLoad();
                getView().hideLoading();
                Intent intent = new Intent("com.sdwxwx.load.video.list.end");
                getView().getContext().sendBroadcast(intent);
            }
        });
    }


    @Override
    public void onLogin() {
        getView().actionStartActivity(LoginActivity.class);
    }

    @Override
    public void onSearch() {
        getView().actionStartActivity(SearchUserActivity.class);
    }


    @Override
    public void loadListData() {

    }
}
