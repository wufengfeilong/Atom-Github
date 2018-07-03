package sdwxwx.com.release.presenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import sdwxwx.com.base.BaseCallback;
import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.bean.MusicBean;
import sdwxwx.com.cons.Constant;
import sdwxwx.com.databinding.OnlineMusicTypeListBinding;
import sdwxwx.com.login.utils.LoginHelper;
import sdwxwx.com.me.service.MeUpdateService;
import sdwxwx.com.release.activity.MusicOnlineTypeActivity;
import sdwxwx.com.release.activity.RecordActivity;
import sdwxwx.com.release.contract.MusicOnlineTypeContract;
import sdwxwx.com.release.model.MusicModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 860117066 on 2018/06/13.
 * 类描述：音乐分类别列表
 */

public class MusicOnlineTypePresenter extends BasePresenter<MusicOnlineTypeContract.View> implements MusicOnlineTypeContract.Presenter {
    List<MusicBean> mList = new ArrayList<>();
    MusicModel mModel;
    OnlineMusicTypeListBinding dataBinding;
    /** 进度条 */
    private ProgressDialog progressDialog;
    /**
     * 播放音乐
     */
    /**
     * 音乐下载
     */
    @Override
    public void downloadOnlineMusic(final MusicBean bean) {
        createPD();
        // 启动Service 开始下载
        MeUpdateService.startUpdate(getView().getContext(), bean.getMusic_url(), bean.getTitle()+".mp3",3, new MeUpdateService.OnProgressListener() {
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
                Intent intent = new Intent(getView().getContext(), RecordActivity.class);
                intent.putExtra(Constant.MUSIC_ONLINE_PATH,getView().getContext().getFilesDir().getPath() + Constant.MUSIC_BOX_NAME+ File.separator+bean.getTitle()+".mp3");
                intent.putExtra(Constant.MUSIC_ONLINE_ID,bean.getId());
                getView().getContext().startActivity(intent);

            }
        });
    }
    /**
     * 下载进度条
     */
    private void createPD(){
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
    @Override
    public void loadListData(String type_id,OnlineMusicTypeListBinding mDataBinding) {
        List<MusicBean> list = new ArrayList<>();
        this.dataBinding = mDataBinding;
        //获取音乐列表
        mModel.getMusicList(LoginHelper.getInstance().getUserId(),"0",type_id,"1","20", new BaseCallback<List<MusicBean>>() {
            @Override
            public void onSuccess(List<MusicBean> data) {
                if (getView() == null) {
                    return;
                }
                getView().bindListData(data);
                if ( data.size() == 0) {
                    dataBinding.noTypeMusic.setVisibility(View.VISIBLE);
                    dataBinding.noTypeMusic.setText("暂无该类型歌曲");
                    dataBinding.musicList.setVisibility(View.GONE);
                } else {
                    dataBinding.noTypeMusic.setVisibility(View.GONE);
                    dataBinding.musicList.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFail(String msg) {
                // 如果不为空NULl，则弹出消息
                if (getView() != null) {
                    getView().showToast(msg);
                }
                dataBinding.noTypeMusic.setVisibility(View.VISIBLE);
                dataBinding.noTypeMusic.setText("暂无该类型歌曲");
                dataBinding.musicList.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void getMusicTypeList() {
        getView().actionStartActivity(MusicOnlineTypeActivity.class);
    }
    @Override
    public void onClickBack(){
        getView().closeActivity();
    }
}
