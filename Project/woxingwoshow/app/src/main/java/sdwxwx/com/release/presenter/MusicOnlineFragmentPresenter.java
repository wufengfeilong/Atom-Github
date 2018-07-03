package sdwxwx.com.release.presenter;


import android.app.ProgressDialog;
import android.content.Intent;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.bean.MusicBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.MusicOnlineFragmentBinding;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.service.MeUpdateService;
import sdwxwx.com.release.activity.MusicOnlineActivity;
import sdwxwx.com.release.contract.MusicOnlineFragmentContract;
import sdwxwx.com.release.model.MusicModel;

import java.io.File;
import java.util.List;

/**
 * Created by 860116042 on 2018/5/16.
 */

public class MusicOnlineFragmentPresenter extends BasePresenter<MusicOnlineFragmentContract.View> implements MusicOnlineFragmentContract.Presenter {
  MusicOnlineFragmentBinding dataBinding;
    /**
     * 进度条
     */
    private ProgressDialog progressDialog;

    /**
     * 音乐下载
     */
    @Override
    public void downloadOnlineMusic(final MusicBean bean) {
        createPD();
        // 启动Service 开始下载
        MeUpdateService.startUpdate(getView().getContext(), bean.getMusic_url(), bean.getTitle() + ".mp3", 3, new MeUpdateService.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                //更新对话框进度条
                progressDialog.setProgress(progress);

            }

            @Override
            public void onSuccess(boolean isSuccess) {
                progressDialog.dismiss();
                //
                //失败提示
                if (!isSuccess) {
                    getView().showToast("下载不成功");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(Constant.MUSIC_ONLINE_PATH,
                        getView().getContext().getFilesDir().getPath()
                                + Constant.MUSIC_BOX_NAME
                                + File.separator
                                + bean.getTitle() + ".mp3");
                intent.putExtra(Constant.MUSIC_ONLINE_ID, bean.getId());
                ((MusicOnlineActivity) getView().getContext()).setResult(-1, intent);
                getView().closeActivity();

            }
        });
    }

    /**
     * 下载进度条
     */
    private void createPD() {
        progressDialog = new ProgressDialog(getView().getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载音乐");
        progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        progressDialog.setMax(100);
        progressDialog.show();
    }

    /**
     * 显示进度框
     *
     * @param message
     */
    private void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(getView().getContext());
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void hideProgressDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    MusicModel mModel;

    //    获取热门歌曲
    @Override
    public void loadPopularMusicData(String page,MusicOnlineFragmentBinding mDataBinding) {
        this.dataBinding = mDataBinding;
        mModel.getRecommendMusicList(LoginHelper.getInstance().getUserId(), page, "20", new BaseCallback<List<MusicBean>>() {
            @Override
            public void onSuccess(List<MusicBean> data) {
                if (getView() == null) {
                    return;
                }
                if ( data.size() == 0) {

//                    getView().showToast("没有更多热门歌曲了。");
//                    dataBinding.noMusic.setVisibility(View.VISIBLE);
//                    dataBinding.noMusic.setText("暂无热门歌曲");
//                    dataBinding.musicOnlineRecyclerview.setVisibility(View.GONE);
                } else {
                    getView().bindListData(data);
//                    dataBinding.noMusic.setVisibility(View.GONE);
//                    dataBinding.musicOnlineRecyclerview.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast(msg);
//                dataBinding.noMusic.setVisibility(View.VISIBLE);
//                dataBinding.noMusic.setText("暂无热门歌曲");
//                dataBinding.musicOnlineRecyclerview.setVisibility(View.GONE);
            }
        });
    }

    //    获取收藏音乐
    @Override
    public void loadCollectMusicData(String page,MusicOnlineFragmentBinding mDataBinding) {

        this.dataBinding = mDataBinding;
        mModel.getMusicList(LoginHelper.getInstance().getUserId(), "1", "0", "1", "20", new BaseCallback<List<MusicBean>>() {
            @Override
            public void onSuccess(List<MusicBean> data) {
                if (getView() == null) {
                    return;
                }
                if ( data.size() == 0) {
//                    getView().showToast("没有更多收藏音乐了。");
//                    dataBinding.noMusic.setVisibility(View.VISIBLE);
//                    dataBinding.noMusic.setText("暂无收藏音乐");
//                    dataBinding.musicOnlineRecyclerview.setVisibility(View.GONE);
                } else {
                    getView().bindListData(data);
//                    dataBinding.noMusic.setVisibility(View.GONE);
//                    dataBinding.musicOnlineRecyclerview.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast( msg);
//                dataBinding.noMusic.setVisibility(View.VISIBLE);
//                dataBinding.noMusic.setText("暂无收藏音乐");
//                dataBinding.musicOnlineRecyclerview.setVisibility(View.GONE);

            }
        });
    }
}
