package woxingwoxiu.com.release.bean;

import android.graphics.Bitmap;

/**
 * Created by 860115032 on 2018/05/23.
 */

public class VideoBean {
    // 视频文件Path
    private String videoName;
    // 视频封面
    private Bitmap videoCover;
    // 视频日期
    private String videoDate;

    /**
     * @return 视频文件Path
     */
    public String getVideoName() {
        return videoName;
    }

    /**
     * @param videoName 视频文件Path
     */
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    /**
     * @return 视频封面
     */
    public Bitmap getVideoCover() {
        return videoCover;
    }

    /**
     * @param videoCover 视频封面
     */
    public void setVideoCover(Bitmap videoCover) {
        this.videoCover = videoCover;
    }

    /**
     * @return 视频日期
     */
    public String getVideoDate() {
        return videoDate;
    }

    /**
     * @param videoDate 视频日期
     */
    public void setVideoDate(String videoDate) {
        this.videoDate = videoDate;
    }
}
