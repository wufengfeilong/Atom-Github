package sdwxwx.com.release.presenter;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.bean.MusicBean;
import sdwxwx.com.bean.MusicTypeBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.release.contract.MusicOnlineContract;
import sdwxwx.com.release.model.MusicModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线音乐列表画面的Presenter
 */

public class MusicOnlinePresenter extends BasePresenter<MusicOnlineContract.View> implements MusicOnlineContract.Presenter {
    List<MusicTypeBean> mList = new ArrayList<>();
    MusicModel mModel;
    @Override
    public void loadListData() {
        mModel.getMusicType(new BaseCallback<List<MusicTypeBean>>() {
            @Override
            public void onSuccess(List<MusicTypeBean> data) {
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
                getView().showToast(msg);
            }
        });



    }
    public void isMusicHave(String musicName) {
        mModel.matchUser( musicName,LoginHelper.getInstance().getUserId(), "1",Constant.DEFAULT_SIZE, new BaseCallback<List<MusicBean>>() {
            @Override
            public void onSuccess(List<MusicBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().ShowSearchData(true,data);
            }

            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().ShowSearchData(false,null);
                getView().showToast(msg);
            }
        });

    }


}
