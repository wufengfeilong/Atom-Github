package sdwxwx.com.bean;

import java.io.Serializable;

/**
 * 用户信息Bean
 */

public class UserBean implements Serializable {

    // 会员编号
    private String id;

    // 推荐人编号
    private String recommender_id;

    //城市编号
    private String city_id;

    //环信账户的名称
    private String easemob_username;

    //环信账户的密码
    private String easemob_password;

    // 手机号码。
    private String mobile;

    // 昵称
    private String nickname;

    // 个性签名
    private String signature;

    // 性别。男、女、未填写。
    private String gender;

    // 出生日期，格式为YYYY-MM-DD。
    private String birthday;

    // 头像的URL地址
    private String avatar_url;

    // 关注数。
    private String follow_count;

    // 粉丝数。
    private String followed_count;

    // 评论数。
    private String comment_count;

    // 团队数（推荐数）。
    private String recommend_count;

    // 会员发布的视频数。
    private String video_count;

    // 会员点赞的视频数。
    private String favorite_video_count;

    // 会员收藏的音乐数。
    private String favorite_music_count;

    // 初始财富值。
    private String initial_wealth;

    // 视频财富值。
    private String video_wealth;

    // 推荐财富值。
    private String recommend_wealth;

    // 是否已认证
    private String is_certified;

    // 是否已互相关注
    private String is_followed;

    // 创建时间，格式为yyyy-MM-dd HH:mm:ss。
    private String create_time;

    //总共财富值
    private String all_wealth;

    /**
     * @return 会员编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 会员编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return 个性签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature 个性签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @return 头像的URL地址
     */
    public String getAvatar_url() {
        return avatar_url;
    }

    /**
     * @param avatar_url 头像的URL地址
     */
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    /**
     * @return 是否已认证
     */
    public String isCertified() {
        return is_certified;
    }

    /**
     * @param certified 是否已认证
     */
    public void setCertified(String certified) {
        is_certified = certified;
    }

    /**
     * @return 是否已互相关注
     */
    public String isFollowed() {
        return is_followed;
    }

    /**
     * @param followed 是否已互相关注
     */
    public void setFollowed(String followed) {
        is_followed = followed;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(String follow_count) {
        this.follow_count = follow_count;
    }

    public String getFollowed_count() {
        return followed_count;
    }

    public void setFollowed_count(String followed_count) {
        this.followed_count = followed_count;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getVideo_count() {
        return video_count;
    }

    public void setVideo_count(String video_count) {
        this.video_count = video_count;
    }

    public String getVideo_wealth() {
        return video_wealth;
    }

    public void setVideo_wealth(String video_wealth) {
        this.video_wealth = video_wealth;
    }

    public String getRecommend_wealth() {
        return recommend_wealth;
    }

    public void setRecommend_wealth(String recommend_wealth) {
        this.recommend_wealth = recommend_wealth;
    }

    public String getFavorite_video_count() {
        return favorite_video_count;
    }

    public void setFavorite_video_count(String favorite_video_count) {
        this.favorite_video_count = favorite_video_count;
    }

    public String getRecommend_count() {
        return recommend_count;
    }

    public void setRecommend_count(String recommend_count) {
        this.recommend_count = recommend_count;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAll_wealth() {
        return String.valueOf(Integer.parseInt(recommend_wealth) + Integer.parseInt(video_wealth));
    }

    public String getRecommender_id() {
        return recommender_id;
    }

    public void setRecommender_id(String recommender_id) {
        this.recommender_id = recommender_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getEasemob_password() {
        return easemob_password;
    }

    public void setEasemob_password(String easemob_password) {
        this.easemob_password = easemob_password;
    }

    public String getEasemob_username() {
        return easemob_username;
    }

    public void setEasemob_username(String easemob_username) {
        this.easemob_username = easemob_username;
    }

    public String getFavorite_music_count() {
        return favorite_music_count;
    }

    public void setFavorite_music_count(String favorite_music_count) {
        this.favorite_music_count = favorite_music_count;
    }

    public String getInitial_wealth() {
        return initial_wealth;
    }

    public void setInitial_wealth(String initial_wealth) {
        this.initial_wealth = initial_wealth;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", recommender_id='" + recommender_id +
                ", city_id='" + city_id +
                ", easemob_username=" + easemob_username+
                ", easemob_password=" + easemob_password +
                ", mobile='" + mobile +
                ", nickname='" + nickname +
                ", signature='" + signature +
                ", gender='" + gender +
                ", birthday='" + birthday +
                ", avatar_url='" + avatar_url +
                ", follow_count=" + follow_count +
                ", followed_count=" + followed_count +
                ", comment_count=" + comment_count +
                ", recommend_count=" + recommend_count +
                ", video_count=" + video_count +
                ", favorite_video_count=" + favorite_video_count +
                ", favorite_music_count=" + favorite_music_count +
                ", initial_wealth=" + initial_wealth +
                ", video_wealth=" + video_wealth +
                ", recommend_wealth=" + recommend_wealth +
                ", is_certified=" + is_certified +
                ", is_followed=" + is_followed +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
