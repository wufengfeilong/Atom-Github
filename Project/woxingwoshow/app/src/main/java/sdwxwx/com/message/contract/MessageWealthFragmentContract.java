package sdwxwx.com.message.contract;

import sdwxwx.com.base.BaseView;
import sdwxwx.com.contract.RecyclerViewContract;
import sdwxwx.com.databinding.MessageFragmentWealthBinding;
import sdwxwx.com.me.bean.VideoWealthBean;

/**
 * Created by 860117066 on 2018/05/17.
 */

public interface MessageWealthFragmentContract {
    interface View extends BaseView,RecyclerViewContract.View<VideoWealthBean> {
        void bindListData(VideoWealthBean bean);
        String getMemberId();
        String getFansHeadUrl();
    }
    interface Presenter {
        void loadListData(MessageFragmentWealthBinding mDataBinding);
    }
}
