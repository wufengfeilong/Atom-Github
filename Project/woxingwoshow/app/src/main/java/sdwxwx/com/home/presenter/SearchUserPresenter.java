package sdwxwx.com.home.presenter;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.bean.TopicInfoBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.home.bean.SearchUserBean;
import sdwxwx.com.home.contract.SearchUserContract;
import sdwxwx.com.home.model.SearchUserModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.activity.QRCodeGenerateActivity;
import sdwxwx.com.me.activity.QRCodeScanActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/14
 * time      15:52
 */
public class SearchUserPresenter extends BasePresenter<SearchUserContract.View> implements SearchUserContract.Presenter {
    List<SearchUserBean> mList = new ArrayList<>();
    SearchUserModel mModel;
    public boolean haveUser;

    @Override
    public void attachView(SearchUserContract.View mvpView) {
        super.attachView(mvpView);
        mModel = new SearchUserModel();
    }

    @Override
    public void cancelClick() {
        getView().closeActivity();
    }

    @Override
    public void myQrCodeClick() {
        getView().actionStartActivity(QRCodeGenerateActivity.class);
    }

    @Override
    public void scanClick() {
        getView().actionStartActivity(QRCodeScanActivity.class);
    }

    @Override
    public void loadTopicData() {
        mModel.getRecommendTopic("1", "4", new BaseCallback<List<TopicInfoBean>>() {
            @Override
            public void onSuccess(List<TopicInfoBean> data) {
                SearchUserBean searchUserBean = new SearchUserBean();
                searchUserBean.setType(0);
                searchUserBean.setRecommendTopicBeans(data);
                mList.add(searchUserBean);
                if (getView() == null) {
                    return;
                }
                getView().bindListData(mList);
                loadPhoneData();
            }

            @Override
            public void onFail(String msg) {
                SearchUserBean searchUserBean = new SearchUserBean();
                searchUserBean.setType(0);
                searchUserBean.setRecommendTopicBeans(null);
                mList.add(searchUserBean);
                if (getView() == null) {
                    return;
                }
                getView().bindListData(mList);
                loadPhoneData();
                getView().showToast(msg);
            }
        });


    }

    @Override
    public void loadUserData() {
        mModel.recommendUsers(LoginHelper.getInstance().getUserId(), new BaseCallback<List<RecommendUserBean>>() {
            @Override
            public void onSuccess(List<RecommendUserBean> data) {
                SearchUserBean searchUserBean = new SearchUserBean();
                searchUserBean.setType(2);
                searchUserBean.setRecommendUserBeans(data);
                mList.add(searchUserBean);
                if (getView() == null) {
                    return;
                }
                getView().bindListData(mList);
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

    private void loadPhoneData() {
        //获取手机通讯录数量
        SearchUserBean searchUserBean = new SearchUserBean();
        searchUserBean.setType(1);
        searchUserBean.setPhoneCount(0);
        mList.add(searchUserBean);
        loadUserData();

    }


    @Override
    public void loadListData() {
        loadTopicData();
    }

    /**
     * 检索用户
     * @param name
     */
    public void searchUser(String name, String page) {
        //查找后台匹配用户名称 true匹配到/false没有匹配
        mModel.matchUser(LoginHelper.getInstance().getUserId(), name,"0",page, Constant.DEFAULT_SIZE, new BaseCallback<List<SearchUserBean.HaveUserBean>>() {
            @Override
            public void onSuccess(List<SearchUserBean.HaveUserBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().haveUser(data);
            }
            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().haveNoUser();
            }
        });
    }
}
