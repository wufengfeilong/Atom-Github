package sdwxwx.com.bean;

import java.io.Serializable;

/**
 * 话题搜索返回的话题信息Bean
 */
public class TopicInfoBean implements Serializable {
    // 话题编号
    private String id;
    // 话题的内容。
    private String title;
    // 该话题下的视频数量。
    private int video_count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
