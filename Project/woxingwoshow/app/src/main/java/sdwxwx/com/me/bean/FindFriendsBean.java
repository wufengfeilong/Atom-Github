package sdwxwx.com.me.bean;

/**
 * Created by 860117073 on 2018/5/23.
 */

public class FindFriendsBean {

    private int id;//会员编号
    private String nickname;//昵称
    private String signature;//个性签名
    private String avatar_url;//头像的url地址
    private int video_wealth;//视频财富值
    private int recommend_wealth;//推荐财富值
    private boolean  is_certified;// 是否已认证
    private boolean  is_followed;// 是否已关注

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean is_certified() {
        return is_certified;
    }

    public void setIs_certified(boolean is_certified) {
        this.is_certified = is_certified;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRecommend_wealth() {
        return recommend_wealth;
    }

    public void setRecommend_wealth(int recommend_wealth) {
        this.recommend_wealth = recommend_wealth;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getVideo_wealth() {
        return video_wealth;
    }

    public void setVideo_wealth(int video_wealth) {
        this.video_wealth = video_wealth;
    }

    public boolean is_followed() {
        return is_followed;
    }

    public void setIs_followed(boolean is_followed) {
        this.is_followed = is_followed;
    }
}
