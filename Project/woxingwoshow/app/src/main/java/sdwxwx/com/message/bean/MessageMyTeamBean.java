package sdwxwx.com.message.bean;

/**
 *
 * 类描述：我的团队一级二级列表
 */

public class MessageMyTeamBean {

    private String level;
    private int id;
    private String nickname;
    private String signature;
    private String avatar_url;
    private int video_wealth;
    private int recommend_wealth;
    private String create_time;
    boolean is_certified;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public boolean isIs_certified() {
        return is_certified;
    }

    public void setIs_certified(boolean is_certified) {
        this.is_certified = is_certified;
    }
}
