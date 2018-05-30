package woxingwoxiu.com.bean;

import java.io.Serializable;

/**
 * 用户信息Bean
 */

public class UserBean implements Serializable {

    // 会员编号
    private int id;

    // 昵称
    private String nickname;

    // 个性签名
    private String signature;

    // 头像的URL地址
    private String avatar_url;

    // 是否已认证
    private boolean is_certified = false;

    // 是否已互相关注
    private boolean is_followed = false;

    String mobile;
    String gender;
    String birthday;
    int follow_count;
    int followed_count;
    int comment_count;
    int video_count;
    int video_wealth;
    int recommend_wealth;


    /**
     * @return 会员编号
     */
    public int getId() {
        return id;
    }

    /**
     * @param id 会员编号
     */
    public void setId(int id) {
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
    public boolean isCertified() {
        return is_certified;
    }

    /**
     * @param certified 是否已认证
     */
    public void setCertified(boolean certified) {
        is_certified = certified;
    }

    /**
     * @return 是否已互相关注
     */
    public boolean isFollowed() {
        return is_followed;
    }

    /**
     * @param followed 是否已互相关注
     */
    public void setFollowed(boolean followed) {
        is_followed = followed;
    }


    public boolean isIs_certified() {
        return is_certified;
    }

    public void setIs_certified(boolean is_certified) {
        this.is_certified = is_certified;
    }

    public boolean isIs_followed() {
        return is_followed;
    }

    public void setIs_followed(boolean is_followed) {
        this.is_followed = is_followed;
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

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }

    public int getFollowed_count() {
        return followed_count;
    }

    public void setFollowed_count(int followed_count) {
        this.followed_count = followed_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
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
}
