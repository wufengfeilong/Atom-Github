package sdwxwx.com.home.bean;

/**
 * create by 860115039
 * date      2018/6/1
 * time      15:18
 * 类描述：bean
 */
public class RecommendUserBean {
    int id;
    String avatar_url;
    String nickname;
    String signature;
    String create_time;
    int video_wealth;
    int recommend_wealth;
    String is_followed;
    String is_certified;
    String status;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
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

    public String getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(String is_followed) {
        this.is_followed = is_followed;
    }


    public String getIs_certified() {
        return is_certified;
    }

    public void setIs_certified(String is_certified) {
        this.is_certified = is_certified;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
