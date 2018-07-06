package sdwxwx.com.release.presenter;

import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.bean.SearchUserBean;
import sdwxwx.com.home.model.SearchUserModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.release.contract.FriendsListContract;

/**
 * @ 好友画面的Presenter
 */

public class FriendsListPresenter extends BasePresenter<FriendsListContract.View> implements FriendsListContract.Presenter {
    /**
     * @param keyWords 搜索关键词
     * @param page 当前批次（页码）
     */
    @Override
    public void getFriendList(String keyWords, final String page) {

        getView().showLoading();
        SearchUserModel.matchUser(LoginHelper.getInstance().getUserId(), keyWords, "1",page, Constant.DEFAULT_SIZE, new BaseCallback<List<SearchUserBean.HaveUserBean>>() {
            @Override
            public void onSuccess(List<SearchUserBean.HaveUserBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().hideLoading();
                if (page.equals(Constant.REQUEST_PAGE)) {
                    getView().bindListData(data);
                } else {
                    getView().loadMoreData(data);
                }
            }

            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().hideLoading();
                List<SearchUserBean.HaveUserBean> data = new ArrayList<>();
                if (page.equals(Constant.REQUEST_PAGE)) {
                    getView().bindListData(data);
                } else {
                    getView().showToast("没有更多好友了");
                    getView().loadMoreData(data);
                }
            }
        });
    }


    /**
     * 点击取消
     */
    @Override
    public void onCancelClick() {
        getView().closeActivity();
    }
}
