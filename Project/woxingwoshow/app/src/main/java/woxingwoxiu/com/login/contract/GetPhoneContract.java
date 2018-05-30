package woxingwoxiu.com.login.contract;

import java.util.List;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.login.bean.GetPhoneBean;

/**
 * Created by 860117076 on 2018/05/29.
 */

public class GetPhoneContract {

    public interface View extends BaseView,RecyclerViewContract.View<GetPhoneBean> {
//        List<GetPhoneBean> getPhoneContacts();
    }

    public interface Presenter extends RecyclerViewContract.Presenter{
            void childEntityData();
            void childEntityTwoData();
    }

}
