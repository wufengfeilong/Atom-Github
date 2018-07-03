package sdwxwx.com.bean;

/**
 * Created by 860115025 on 2018/06/14.
 */

public class MusicBean {
    //新规
    /** 类型名称 */
    private String name;

    /** 类型编号 */
    private String id;
    /** 标题 */
    private String title;
    /** 艺术家，音乐的作者名称 */
    private String artist;
    /** 封面的URL地址 */
    private String cover_url;
    /** 音乐的URL地址 */
    private String music_url;
    /** 播放时长（秒数） */
    private String duration;
    /** 使用该音乐的视频数量 */
    private String video_count;
    /** 创建时间 */
    private String create_time;
    /** 会员是否已收藏该音乐 */
    private String is_favorited;

    private boolean isPlay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_favorited() {
        return is_favorited;
    }

    public void setIs_favorited(String is_favorited) {
        this.is_favorited = is_favorited;
    }

    public String getMusic_url() {
        return music_url;
    }

    public void setMusic_url(String music_url) {
        this.music_url = music_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo_count() {
        return video_count;
    }

    public void setVideo_count(String video_count) {
        this.video_count = video_count;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }
}
