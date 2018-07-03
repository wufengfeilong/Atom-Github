package sdwxwx.com.release.presenter;

import sdwxwx.com.base.BasePresenter;
import sdwxwx.com.release.contract.RecordContract;

/**
 * 准备录视频画面的Presenter
 */

public class RecordPresenter extends BasePresenter<RecordContract.View> implements RecordContract.Presenter {

    private String TAG = "RecordPresenter";

    /**
     * 关闭按钮点击事件
     */
    @Override
    public void onCloseClick() {
        getView().backOffClick();
    }

    /**
     * 音乐按钮点击事件
     */
    @Override
    public void onMusicClick() {
        getView().onMusicClick();
    }

    /**
     * 切换摄像头按钮点击事件
     */
    @Override
    public void onReverseCameraClick() {
        getView().onSwitchCamera();
    }

    /**
     * 更多操作按钮点击事件
     */
    @Override
    public void onMoreClick() {

        getView().onMoreClick();
    }

    /**
     * 更多操作显示按钮点击事件
     *
     * @param itemId 区分点击位置
     */
    @Override
    public void onMoreItemClick(int itemId) {
        getView().onMoreItemClick(itemId);
    }

    /**
     * back键点击事件
     */
    @Override
    public void onBackOffClick() {
        getView().backOffClick();
    }

    /**
     * 开始录制按钮点击事件
     */
    @Override
    public void onRecordClick() {
        getView().onRecordClick();
    }

    /**
     * 下一步按钮点击事件
     */
    @Override
    public void onNextClick() {
        getView().onNextClick();
    }

    /**
     * 美颜按钮点击事件
     */
    @Override
    public void onFairClick() {
        getView().onFairClick();
    }

    /**
     * 暂停按钮点击事件
     */
    @Override
    public void onStopRecordClick() {
        getView().onStopRecordClick();
    }

    /**
     * 保存按钮点击事件
     */
    @Override
    public void onSaveBtnClick() {
        getView().onSaveBtnClick();
    }

    /**
     * 录制再启动按钮点击事件
     */
    @Override
    public void onRecordRestartClick() {
        getView().onRecordRestartClick();
    }

    /**
     * 启动从相册中选择
     */
    @Override
    public void onStartAlbum() {

        getView().onStartAlbum();
    }

    /**
     * 删除上一段视频
     */
    @Override
    public void deleteLastVideo() {

        getView().deleteLastVideo();
    }

    /**
     * 退出在线音乐
     */
    @Override
    public void onMusicExit() {
        getView().onMusicExit();
    }

    /**
     * 已设定的音乐按钮点击事件
     */
    @Override
    public void onMusicOnlineClick() {
        getView().onMusicOnlineClick();
    }

    /**
     * 变更在线音乐
     */
    @Override
    public void onMusicChange() {
        getView().onMusicChange();
    }

    /**
     * 取消变更在线音乐
     */
    @Override
    public void onCancelMusicChange() {
        getView().onCancelMusicChange();
    }
}
