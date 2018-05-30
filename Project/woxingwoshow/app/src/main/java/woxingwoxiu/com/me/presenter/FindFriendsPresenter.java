package woxingwoxiu.com.me.presenter;

import java.util.ArrayList;
import java.util.List;

import woxingwoxiu.com.base.BasePresenter;
import woxingwoxiu.com.home.activity.SearchUserActivity;
import woxingwoxiu.com.me.activity.QRCodeGenerateActivity;
import woxingwoxiu.com.me.activity.QRCodeScanActivity;
import woxingwoxiu.com.me.bean.FindFriendsBean;
import woxingwoxiu.com.me.contract.FindFriendsContract;

/**
 * Created by 860117073 on 2018/5/22.
 */

public class FindFriendsPresenter extends BasePresenter<FindFriendsContract.View> implements FindFriendsContract.Presenter {

    List<FindFriendsBean> mList=new ArrayList<>();

    @Override
    public void loadListData() {
        mList.clear();
        FindFriendsBean bean1 =new FindFriendsBean();
        bean1.setUserName("雷神");
        bean1.setSignature("我就是雷神，没错");
        mList.add(bean1);
        FindFriendsBean bean2 =new FindFriendsBean();
        bean2.setUserName("洛基");
        bean2.setSignature("我就是洛基，哈哈");
        mList.add(bean2);
        FindFriendsBean bean3 =new FindFriendsBean();
        bean3.setUserName("幻世");
        bean3.setSignature("幻世就是我，恩额");
        mList.add(bean3);
        getView().bindListData(mList);
    }
    @Override
    public void myQrCodeClick() {
        //我的二维码
        getView().actionStartActivity(QRCodeGenerateActivity.class);
    }

    public void toScan(){
        //扫码功能
        getView().actionStartActivity(QRCodeScanActivity.class);


    }
    public void toSearchUser(){
        //跳转到搜索界面
        getView().actionStartActivity(SearchUserActivity.class);

    }

    public void back(){
        //返回键操作
        getView().closeActivity();
    }
}
