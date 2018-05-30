package woxingwoxiu.com.home.presenter;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.home.bean.SearchUserBean;
import woxingwoxiu.com.home.contract.SearchUserContract;
import woxingwoxiu.com.me.activity.QRCodeGenerateActivity;
import woxingwoxiu.com.me.activity.QRCodeScanActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/14
 * time      15:52
 */
public class SearchUserPresenter extends BasePresenter<SearchUserContract.View> implements SearchUserContract.Presenter {
    List<SearchUserBean> mList = new ArrayList<>();

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
        SearchUserBean searchUserBean = new SearchUserBean();
        List<SearchUserBean.RecommendTopicBean> topicBeanList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SearchUserBean.RecommendTopicBean topicBean = new SearchUserBean.RecommendTopicBean();
            topicBean.setTitle("Found Beauty" + i);
            topicBean.setCount("10-" + i);
            topicBeanList.add(topicBean);
        }
        searchUserBean.setType(0);
        searchUserBean.setRecommendTopicBeans(topicBeanList);
        mList.add(searchUserBean);

    }

    @Override
    public void loadUserData() {
        SearchUserBean searchUserBean = new SearchUserBean();
        List<SearchUserBean.RecommendUserBean> userBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SearchUserBean.RecommendUserBean userBean = new SearchUserBean.RecommendUserBean();
            userBean.setFollow(true);
            userBean.setNickName("China" + i);
            userBean.setNickName("introduce" + i);
            userBeanList.add(userBean);
        }
        searchUserBean.setType(2);
        searchUserBean.setRecommendUserBeans(userBeanList);
        mList.add(searchUserBean);
    }

    @Override
    public void loadListData() {
        loadTopicData();
        //获取手机通讯录数量
        SearchUserBean searchUserBean = new SearchUserBean();
        searchUserBean.setType(1);
        searchUserBean.setPhoneCount(2);
        mList.add(searchUserBean);
        loadUserData();
        getView().bindListData(mList);

    }

    public boolean isUserHave(){
        //查找后台匹配用户名称 true匹配到/false没有匹配

        return true;
    }

}
