package sdwxwx.com.home.contract;

/**
 * create by 860115039
 * date      2018/6/1
 * time      15:59
 * 通讯录验证返回的Bean
 */
public class ContactBean  {


    int id;    //	会员编号。
    String mobile;    //	手机号码。
    int is_followed;    //	是否已关注会员本人。
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

    public int isIs_followed() {
        return is_followed;
    }

    public void setIs_followed(int is_followed) {
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
