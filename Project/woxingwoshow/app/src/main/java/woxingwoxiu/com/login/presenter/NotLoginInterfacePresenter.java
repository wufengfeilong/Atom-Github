package woxingwoxiu.com.login.presenter;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.home.activity.SearchUserActivity;
import woxingwoxiu.com.home.bean.CategoryVideoListBean;
import woxingwoxiu.com.login.activity.LoginActivity;
import woxingwoxiu.com.login.bean.NotLoginInterfaceBean;
import woxingwoxiu.com.login.contract.NotLoginInterfaceContract;

/**
 * Created by 丁胜胜 on 2018/05/23.
 */

public class NotLoginInterfacePresenter extends BasePresenter<NotLoginInterfaceContract.View>
                    implements NotLoginInterfaceContract.Presenter{

    @Override
    public void loadListData() {
        List<NotLoginInterfaceBean> list = new ArrayList<>();
        NotLoginInterfaceBean bean = new NotLoginInterfaceBean();
        bean.setCreateTime("2018-04-05");
        bean.setNickName("dada");
        bean.setTitle("乞丐版的自我救赎1");
        bean.setTunmbupCount("1234");
        bean.setDistance("12");
        list.add(bean);
        NotLoginInterfaceBean bean1 = new NotLoginInterfaceBean();
        bean1.setCreateTime("2018-04-05");
        bean1.setNickName("dada");
        bean1.setTitle("乞丐版的自我救赎2乞丐版的自我救赎2乞丐版的自我救赎2");
        bean1.setTunmbupCount("1234");
        bean1.setDistance("15");
        list.add(bean1);
        NotLoginInterfaceBean bean2 = new NotLoginInterfaceBean();
        bean2.setCreateTime("2018-04-05");
        bean2.setNickName("dada");
        bean2.setTitle("乞丐版的自我救赎3");
        bean2.setDistance("18");
        bean2.setTunmbupCount("1234");
        list.add(bean2);
        NotLoginInterfaceBean bean3 = new NotLoginInterfaceBean();
        bean3.setCreateTime("2018-04-05");
        bean3.setNickName("dada");
        bean3.setTitle("乞丐版的自我救赎4");
        bean3.setDistance("20");
        bean3.setTunmbupCount("1234");
        list.add(bean3);
        getView().bindListData(list);
    }

    @Override
    public void onLogin() {
        getView().actionStartActivity(LoginActivity.class);
    }

    @Override
    public void onSearch() {
        getView().actionStartActivity(SearchUserActivity.class);
    }


}
