package sdwxwx.com.login.bean;

import java.util.List;

import sdwxwx.com.home.contract.ContactBean;

/**
 * Created by 丁胜胜 on 2018/05/29.
 */

public class GetPhoneBean {

    private String header;
    private String footer;
    private List<ChildEntity> children;
    private List<ChildEntity> childrenTwo;
    private List<ContactBean> contactBeans;

    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public List<ChildEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ChildEntity> children) {
        this.children = children;
    }

    public void setChildrenTwo(List<ChildEntity> childrenTwo) {
        this.childrenTwo = childrenTwo;
    }

    public List<ChildEntity> getChildrenTwo() {
        return childrenTwo;
    }

    public List<ContactBean> getContactBeans() {
        return contactBeans;
    }

    public void setContactBeans(List<ContactBean> contactBeans) {
        this.contactBeans = contactBeans;
    }


    public class GetPhoneBeanList {

        String member_id;    //	会员编号。
        String mobile;    //	手机号码。
        String is_followed;    //	是否已关注会员本人。
        String nickname; // 昵称。
        String avatar_url; // 头像的URL地址。

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIs_followed() {
            return is_followed;
        }

        public void setIs_followed(String is_followed) {
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

}
