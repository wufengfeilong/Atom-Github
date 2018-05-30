package woxingwoxiu.com.home.bean;

/**
 * create by 860115039
 * date      2018/5/30
 * time      14:16
 */
public class PlayVideoBean {
    int id;              // 视频编号。
    int topic_id;        // 话题编号。
    int ksyun_id;        // 金山云编号。
    String title;        //	标题。
    String description;  //	描述。
    String cover_url;    //	封面。
    int status;          // 发布状态。0表示待发布，1表示已发布，2表示已屏蔽。
    float longitude;     //	经度。
    float latitude;      //	维度。
    int like_count;      //	点赞数。
    int share_count;     //	分享数。
    int comment_count;   //	评论数。
    String create_time;     //	发布时间。
    int member_id;       //	发布会员编号。
    String nickname;     //	发布会员的昵称。
    String avatar_url;   //	发布会员的头像URL地址。


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

    public int getKsyun_id() {
        return ksyun_id;
    }

    public void setKsyun_id(int ksyun_id) {
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
}
