package sdwxwx.com.home.presenter;

import android.content.Intent;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.home.contract.TopicVideoListContract;
import sdwxwx.com.home.model.TopicVideoListModel;
import sdwxwx.com.login.utils.LoginHelper;

import java.util.List;

/**
 * Created by 860117073 on 2018/5/29.
 */

public class TopicVideoListPresenter extends BasePresenter<TopicVideoListContract.View> implements TopicVideoListContract.Presenter{
    TopicVideoListModel mModel;
    public void back(){
        //返回键操作
        getView().finishThis();
    }

    @Override
    public void loadListData(String page) {
        getView().showLoading();
       //取得话题编号
        String topicId = getView().getTopicId();
        mModel.getTopicVideo(LoginHelper.getInstance().getUserId(), "0", "0", "0", topicId, page, new BaseCallback<List<PlayVideoBean>>() {
            @Override
            public void onSuccess(List<PlayVideoBean> data) {
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
                getView().showToast(msg);
                getView().hideLoading();
                getView().getSpringView().onFinishFreshAndLoad();
                Intent intent = new Intent("com.sdwxwx.load.video.list.end");
                getView().getContext().sendBroadcast(intent);

            }
        });


    }

    @Override
    public void loadListData() {

    }
}
