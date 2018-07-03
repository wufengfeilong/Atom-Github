package sdwxwx.com.release.presenter;

import android.util.Log;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.bean.TopicInfoBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.release.contract.AddTopicContract;
import sdwxwx.com.release.model.AddTopicModel;

import java.util.List;

/**
 * 添加话题画面的Presenter
 */

public class AddTopicPresenter extends BasePresenter<AddTopicContract.View> implements AddTopicContract.Presenter {
    private final String TAG = "AddTopicPresenter";

    private AddTopicModel addTopicModel;

    @Override
    public void attachView(AddTopicContract.View mvpView) {
        super.attachView(mvpView);
        addTopicModel = new AddTopicModel();
    }

    /**
     * 获取话题列表信息
     * @param keyword 搜索关键词
     * @param page 当前批次（页码）
     */
    @Override
    public void getAddTopicList(String keyword, String page) {
        Log.d(TAG, "getAddTopicList start keyword = " + keyword + "\npage = " + page);
        getView().showLoading();
        // keyword 搜索关键词,page 当前批次（页码）,size 每个批次的视频数量
        addTopicModel.getAddTopicList(keyword, page, Constant.DEFAULT_SIZE, new BaseCallback<List<TopicInfoBean>>() {
            @Override
            public void onSuccess(List<TopicInfoBean> data) {
                Log.d(TAG, "getAddTopicList onSuccess data.size() = " + data.size());
                if (getView() == null) {
                    return;
                }
                getView().hideLoading();

                getView().refreshListData(data);
            }

            @Override
            public void onFail(String msg) {
                Log.e(TAG, "getAddTopicList onFail msg = " + msg);
                if (getView() == null) {
                    return;
                }
                getView().hideLoading();

                getView().showToast(msg);
            }
        });
    }

    /**
     * 点击取消
     */
    @Override
    public void onCancelClick() {
        getView().closeActivity();
    }

    /**
     * 选择不使用话题
     */
    @Override
    public void selectNoTopic() {
        getView().setNoTopic();
    }
}
