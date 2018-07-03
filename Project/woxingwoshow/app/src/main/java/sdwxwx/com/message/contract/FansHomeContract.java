package sdwxwx.com.message.contract;

import com.liaoinstan.springview.widget.SpringView;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.bean.UserBean;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.home.bean.PlayVideoBean;

/**
 * Created by 860117073 on 2018/5/16.
 */

public interface FansHomeContract {

    interface View extends BaseView,RecyclerViewContract.View<PlayVideoBean>,SpringView.OnFreshListener {
        void bindFansInfor(UserBean mBean);
        android.view.View getAttention();
        android.view.View cancelAttention();
        void onShare();
        String getMemberId();
        String getEmId();
        String getFansHeadUrl();
        String getNickname();
        void clickHederImg();
        SpringView getSpringView();
    }

    interface Presenter extends RecyclerViewContract.Presenter{

        void getAttention();

        void cancelAttention();

        void toFansList();

        void toFansAttention();

        void toTeam();

        void toMoney();

        void onClickFansShare();

        void onClickHeaderImg();

        /**
         * 发送私信
         */
        void sendMessage();

        void loadListData(String page);
    }
}
