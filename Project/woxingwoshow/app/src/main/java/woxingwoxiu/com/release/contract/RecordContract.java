package woxingwoxiu.com.release.contract;

import woxingwoxiu.com.base.BaseView;

/**
 * 准备录视频画面的Contract
 */

public class RecordContract {
    public interface View extends BaseView {
        // BackKey点击事件
        void backOffClick();

        /**
         * 转换摄像头按钮点击事件
         */
        void onSwitchCamera();

        /**
         * 音乐按钮点击事件
         */
        void onMusicClick();

        // 开始录制点击事件
        void onRecordClick();

        // 下一步按钮点击事件
        void onNextClick();

        // 录像画面更多按钮点击事件
        void onMoreClick();

        /**
         * 更多操作显示按钮点击事件
         * @param itemId 区分点击位置
         */
        void onMoreItemClick(int itemId);

        /**
         * 美颜按钮点击事件
         */
        void onFairClick();

        /**
         * 暂停按钮点击事件
         */
        void onStopRecordClick();

        /**
         * 保存按钮点击事件
         */
        void onSaveBtnClick();

        /**
         * 录制再启动按钮点击事件
         */
        void onRecordRestartClick();

        /**
         * 启动从相册中选择
         */
        void onStartAlbum();

        /**
         * 删除上一段视频
         */
        void deleteLastVideo();
    }

    public interface Presenter {

        void onCloseClick();

        /**
         * 音乐按钮点击事件
         */
        void onMusicClick();

        void onReverseCameraClick();

        /**
         * 更多操作按钮点击事件
         */
        void onMoreClick();

        /**
         * 更多操作显示按钮点击事件
         * @param itemId 区分点击位置
         */
        void onMoreItemClick(int itemId);

        /**
         * BackKey点击事件
         */
        void onBackOffClick();

        /**
         * 开始录制点击事件
         */
        void onRecordClick();

        /**
         * 下一步按钮点击事件
         */
        void onNextClick();

        /**
         * 美颜按钮点击事件
         */
        void onFairClick();

        /**
         * 暂停按钮点击事件
         */
        void onStopRecordClick();

        /**
         * 保存按钮点击事件
         */
        void onSaveBtnClick();

        /**
         * 录制再启动按钮点击事件
         */
        void onRecordRestartClick();

        /**
         * 启动从相册中选择
         */
        void onStartAlbum();

        /**
         * 删除上一段视频
         */
        void deleteLastVideo();

    }
}
