package woxingwoxiu.com.message.contract;

import android.widget.ImageView;
import android.widget.LinearLayout;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.message.bean.FansBean;

/**
 * Created by 860117073 on 2018/5/16.
 */

public interface FansHomeContract {

    interface View extends BaseView,RecyclerViewContract.View<FansBean> {
        ImageView getAttention();
        LinearLayout cancelAttention();
    }

    interface Presenter extends RecyclerViewContract.Presenter{

        void getAttention();

        void cancelAttention();

        void toFansList();

        void toFansAttention();

        void toTeam();

        void toMoney();

    }
}
