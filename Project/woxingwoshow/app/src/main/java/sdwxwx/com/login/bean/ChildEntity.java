package sdwxwx.com.login.bean;

/**
 *  Created by 丁胜胜 on 2018/05/29.
 * 子项数据的实体类
 * 类描述：手机通讯录中待关注列表
 */
public class ChildEntity {

    int id;    //	会员编号。
    String mobile;    //	手机号码。
    boolean is_followed;    //	是否已关注会员本人。
    String nickname; // 昵称。
    String avatar_url; // 头像的URL地址。

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

    public boolean isIs_followed() {
        return is_followed;
    }

    public void setIs_followed(boolean is_followed) {
        this.is_followed = is_followed;
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
