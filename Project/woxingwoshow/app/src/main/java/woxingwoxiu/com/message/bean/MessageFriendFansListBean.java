package woxingwoxiu.com.message.bean;

/**
 *
 * 类描述：好友粉丝列表
 */

public class MessageFriendFansListBean {
    /*
     member_id string	会员编号 get
 * */
    private int id;//会员编号
    private String nickname;//昵称
    private String signature;//个性签名
    private String avatar_url;//头像的url地址
    boolean  is_certified;//是否已认证
    boolean  is_followed;//是否已互相关注

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
}
