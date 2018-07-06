package sdwxwx.com.release.presenter;

import android.app.ProgressDialog;
import android.content.Intent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.bean.MusicBean;
import sdwxwx.com.bean.MusicTypeBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.service.MeUpdateService;
import sdwxwx.com.release.activity.MusicOnlineActivity;
import sdwxwx.com.release.contract.MusicOnlineContract;
import sdwxwx.com.release.model.MusicModel;

/**
 * 在线音乐列表画面的Presenter
 */

public class MusicOnlinePresenter extends BasePresenter<MusicOnlineContract.View> implements MusicOnlineContract.Presenter {
    List<MusicTypeBean> mList = new ArrayList<>();
    MusicModel mModel;
    @Override
    public void loadListData() {
        mModel.getMusicType(new BaseCallback<List<MusicTypeBean>>() {
            @Override
            public void onSuccess(List<MusicTypeBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().bindListData(data);
            }
            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().showToast(msg);
            }
        });



    }
    public void isMusicHave(String musicName) {
        mModel.matchUser( musicName,LoginHelper.getInstance().getUserId(), "1",Constant.DEFAULT_SIZE, new BaseCallback<List<MusicBean>>() {
            @Override
            public void onSuccess(List<MusicBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().ShowSearchData(true,data);
            }

            @Override
            public void onFail(String msg) {
                if (getView() == null) {
                    return;
                }
                getView().ShowSearchData(false,null);
                getView().showToast(msg);
            }
        });

    }
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

}
