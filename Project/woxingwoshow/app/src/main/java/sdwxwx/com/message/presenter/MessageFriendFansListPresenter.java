package sdwxwx.com.message.presenter;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.home.bean.RecommendUserBean;
import sdwxwx.com.message.contract.MessageFriendFansListContract;
import sdwxwx.com.message.model.MessageFansListModel;

import java.util.List;

/**
 *
 * 类描述：粉丝、关注列表Presenter
 */

public class MessageFriendFansListPresenter
        extends BasePresenter<MessageFriendFansListContract.View> implements MessageFriendFansListContract.Presenter{
       MessageFansListModel mModel;
       RecommendUserBean fansListBean ;

    @Override
    public void loadListData(String type, String member_id,String page) {
        // 弹出加载对话框
        getView().showLoading();
        // 如果是粉丝列表
        if ("1".equals(type)) {
            // 调用粉丝列表的接口
            mModel.getMessageFansList(member_id,page,"20",new BaseCallback<List<RecommendUserBean>>() {
                @Override
                public void onSuccess(List<RecommendUserBean> data) {
                    if (getView() == null) {
                        return;
                    }
                    getView().bindListData(data);
                    getView().hideLoading();
                }
                @Override
                public void onFail(String msg) {
                    if (getView() == null) {
                        return;
                    }
                    getView().showToast("粉丝列表请求失败"+msg);
                    getView().hideLoading();
                }
            });

        } else {
            // 调用关注列表的接口
            mModel.getMessageAttentionsList(member_id, page,"20",new BaseCallback<List<RecommendUserBean>>() {
                @Override
                public void onSuccess(List<RecommendUserBean> data) {
                    if (getView() == null) {
                        return;
                    }
                    getView().bindListData(data);
                    getView().hideLoading();
                }
                @Override
                public void onFail(String msg) {
                    if (getView() == null) {
                        return;
                    }
                    getView().showToast("关注列表请求失败"+msg);
                    getView().hideLoading();
                }
            });
        }
    }
    //返回按钮的点击事件
    @Override
    public void onClickBack(){
        getView().closeActivity();
    }

    @Override
    public void loadListData() {

    }
}
