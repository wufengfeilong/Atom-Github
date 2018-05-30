package woxingwoxiu.com.near.presenter;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.home.bean.CategoryVideoListBean;
import woxingwoxiu.com.near.contract.NearFragmentContract;

import java.util.ArrayList;
import java.util.List;

/**
 * create by 860115039
 * date      2018/5/9
 * time      9:51
 */
public class NearFragmentPresenter extends BasePresenter<NearFragmentContract.View> implements NearFragmentContract.Presenter {

    @Override
    public void loadListData() {
        List<CategoryVideoListBean> list = new ArrayList<>();
        CategoryVideoListBean bean = new CategoryVideoListBean();
        bean.setCreateTime("2018-04-05");
        bean.setNickName("dada");
        bean.setTitle("乞丐版的自我救赎1");
        bean.setTunmbupCount("1234");
        bean.setDistance("12");
        list.add(bean);
        CategoryVideoListBean bean1 = new CategoryVideoListBean();
        bean1.setCreateTime("2018-04-05");
        bean1.setNickName("dada");
        bean1.setTitle("乞丐版的自我救赎2乞丐版的自我救赎2乞丐版的自我救赎2");
        bean1.setTunmbupCount("1234");
        bean1.setDistance("15");
        list.add(bean1);
        CategoryVideoListBean bean2 = new CategoryVideoListBean();
        bean2.setCreateTime("2018-04-05");
        bean2.setNickName("dada");
        bean2.setTitle("乞丐版的自我救赎3");
        bean2.setDistance("18");
        bean2.setTunmbupCount("1234");
        list.add(bean2);
        CategoryVideoListBean bean3 = new CategoryVideoListBean();
        bean3.setCreateTime("2018-04-05");
        bean3.setNickName("dada");
        bean3.setTitle("乞丐版的自我救赎4");
        bean3.setDistance("20");
        bean3.setTunmbupCount("1234");
        list.add(bean3);
        getView().bindListData(list);
    }
}
