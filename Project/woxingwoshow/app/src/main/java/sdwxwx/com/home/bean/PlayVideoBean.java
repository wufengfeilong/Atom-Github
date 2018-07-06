package sdwxwx.com.home.bean;

/**
 * create by 860115039
 * date      2018/5/30
 * time      14:16
 */
public class PlayVideoBean {
    int id;              // 视频编号。
    int topic_id;        // 话题编号。
    String ksyun_id;     // 金山云编号。
    String url;          // 视频的URL地址。
    String city_id;      // 城市编号。
    String title;        //	标题。
    String description;  //	描述。
    String cover_url;    //	封面。
    int status;          // 发布状态。0表示待发布，1表示已发布，2表示已屏蔽。
    float longitude;     //	经度。
    float latitude;      //	维度。
    int like_count;      //	点赞数。
    int share_count;     //	分享数。
    int comment_count;   //	评论数。
    String create_time;  //	发布时间。
    int member_id;       //	发布会员编号。
    String nickname;     //	发布会员的昵称。
    String avatar_url;   //	发布会员的头像URL地址。
    String is_liked;     // 是否已点赞视频。
    String music_id;     // 音乐编号。
    String is_followed;  // 是否关注过视频作者。
    String Video_type;   // 是否为广告视频,0为会员视频，1为广告视频
    String Skip_url;     // 广告视频跳转路径


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getKsyun_id() {
        return ksyun_id;
    }

    public void setKsyun_id(String ksyun_id) {
        this.ksyun_id = ksyun_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }

    public String getMusic_id() {
        return music_id;
    }

    public void setMusic_id(String music_id) {
        this.music_id = music_id;
    }

    public String getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(String is_followed) {
        this.is_followed = is_followed;
    }

    public String getVideo_type() {
        return Video_type;
    }

    public void setVideo_type(String video_type) {
        Video_type = video_type;
    }

    public String getSkip_url() {
        return Skip_url;
    }

    public void setSkip_url(String skip_url) {
        Skip_url = skip_url;
    }
}
