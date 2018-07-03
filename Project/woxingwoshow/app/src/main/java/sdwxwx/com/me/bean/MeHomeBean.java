package sdwxwx.com.me.bean;

/**
 * Created by 860117066 on 2018/05/29.
 */

public class MeHomeBean {
    private int id; //会员编号

    private String mobile;//手机号码

    private String nickname;//昵称

    private String signature;//个性签名

    private String avatar_url; //粉丝头像url

    private int followed_count; //拥有粉丝数量

    private int follow_count; //拥有关注数量

    private int recommend_count; //团队数量（推荐数）该会员发展推荐的一级和二级会员的总数

    private int video_count;  //视频数量

    private int favorite_count;  //点赞视频数量

    private int video_wealth;  //视频财富值

    private int recommend_wealth; //推荐财富值

    private String gender; //性别

    private String birthday; //出生日期

    private String cover_url; //封面url

    private String comment_count; //评论数

    private String create_time; //注册时间，格式为yyyy-MM-dd HH:mm:ss

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getFollowed_count() {
        return followed_count;
    }

    public void setFollowed_count(int followed_count) {
        this.followed_count = followed_count;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }

    public int getRecommend_count() {
        return recommend_count;
    }

    public void setRecommend_count(int recommend_count) {
        this.recommend_count = recommend_count;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public int getVideo_wealth() {
        return video_wealth;
    }

    public void setVideo_wealth(int video_wealth) {
        this.video_wealth = video_wealth;
    }

    public int getRecommend_wealth() {
        return recommend_wealth;
    }

    public void setRecommend_wealth(int recommend_wealth) {
        this.recommend_wealth = recommend_wealth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
