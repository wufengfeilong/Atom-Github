package woxingwoxiu.com.letter.contract;

import woxingwoxiu.com.base.BaseView;
import woxingwoxiu.com.contract.RecyclerViewContract;
import woxingwoxiu.com.letter.bean.PrivateLetterBean;

/**
 * Created by 860117073 on 2018/5/28.
 */

public interface LetterListContract {
    interface View extends BaseView,RecyclerViewContract.View<PrivateLetterBean.LetterBean> {
        void finishThis();
    }
    interface Presenter extends RecyclerViewContract.Presenter {
    }
}
