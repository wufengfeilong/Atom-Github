package sdwxwx.com.me.presenter;


import android.content.Intent;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.databinding.MeHomeVideoBinding;
import sdwxwx.com.home.bean.PlayVideoBean;
import sdwxwx.com.home.model.HomeCategoryVideoModel;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.contract.MeHomeFragmentContract;
import sdwxwx.com.play.model.PlayVideoFragmentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 860116042 on 2018/5/16.
 */

public class MeHomeFragmentPresenter extends BasePresenter<MeHomeFragmentContract.View> implements MeHomeFragmentContract.Presenter {

    private MeHomeVideoBinding dataBinding;
    List<PlayVideoBean> mList=new ArrayList<>();

    HomeCategoryVideoModel mModel;
    PlayVideoFragmentModel mLikeModel;
    @Override
    public void attachView(MeHomeFragmentContract.View mvpView) {
        super.attachView(mvpView);
        mModel = new HomeCategoryVideoModel();
    }

    @Override
    public void loadMeVideoData(String page,MeHomeVideoBinding mDataBinding) {
        getView().showLoading();
        this.dataBinding = mDataBinding;
        // 调用接口获取视频列表
        mModel.getMyUpVideoList(LoginHelper.getInstance().getUserId(),
                "0", "0","0",LoginHelper.getInstance().getUserId(),page, new BaseCallback<List<PlayVideoBean>>() {
            @Override
            public void onSuccess(List<PlayVideoBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().bindListData(data);
                getView().hideLoading();
//                if ( data.size() == 0) {
//                    dataBinding.tempNoVideo.setVisibility(View.VISIBLE);
//                    dataBinding.tempNoVideo.setText("TA暂时没有发布视频");
//                    dataBinding.homeVideoRecyclerView.setVisibility(View.GONE);
//                } else {
//                    dataBinding.tempNoVideo.setVisibility(View.GONE);
//                    dataBinding.homeVideoRecyclerView.setVisibility(View.VISIBLE);
//                }
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
//                dataBinding.homeVideoRecyclerView.setVisibility(View.GONE);
//                dataBinding.tempNoVideo.setText("TA暂时没有发布视频");
//                dataBinding.tempNoVideo.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void loadUpVideoData(String page,MeHomeVideoBinding mDataBinding) {
//        getView().showLoading();
//        this.dataBinding = mDataBinding;
        // 获取点赞过的视频列表
        mModel.getMyUpVideoList(LoginHelper.getInstance().getUserId(),"1", "0","0",LoginHelper.getInstance().getUserId(),page,
                new BaseCallback<List<PlayVideoBean>>() {
                    @Override
                    public void onSuccess(List<PlayVideoBean> data) {
                        if (getView() == null) {
                            return;
                        }
                        getView().bindListData(data);
//                        getView().hideLoading();
//                        if ( data.size() == 0) {
//                            dataBinding.tempNoVideo.setVisibility(View.VISIBLE);
//                            dataBinding.tempNoVideo.setText("TA暂时没有点赞视频");
//                            dataBinding.homeVideoRecyclerView.setVisibility(View.GONE);
//                        } else {
//                            getView().getSpringView().onFinishFreshAndLoad();
//                            dataBinding.tempNoVideo.setVisibility(View.GONE);
//                            dataBinding.homeVideoRecyclerView.setVisibility(View.VISIBLE);
//                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        if (getView() == null) {
                            return;
                        }
                        getView().showToast(msg);
//                        getView().hideLoading();
                        getView().getSpringView().onFinishFreshAndLoad();
                        Intent intent = new Intent("com.sdwxwx.load.video.list.end");
                        getView().getContext().sendBroadcast(intent);
//                        dataBinding.homeVideoRecyclerView.setVisibility(View.GONE);
//                        dataBinding.tempNoVideo.setText("TA暂时没有点赞视频");
//                        dataBinding.tempNoVideo.setVisibility(View.VISIBLE);
                    }
                });
    }

}
