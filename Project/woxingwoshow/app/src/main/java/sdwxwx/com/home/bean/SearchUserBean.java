package sdwxwx.com.home.bean;

import java.io.Serializable;
import java.util.List;

import sdwxwx.com.bean.TopicInfoBean;

/**
 * create by 860115039
 * date      2018/5/15
 * time      15:31
 */
public class SearchUserBean {
    int type;
    int phoneCount;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPhoneCount() {
        return phoneCount;
    }

    public void setPhoneCount(int phoneCount) {
        this.phoneCount = phoneCount;
    }

    List<TopicInfoBean> recommendTopicBeans;
    List<RecommendUserBean> recommendUserBeans;
    List<HaveUserBean> haveUserBeans;

    public List<HaveUserBean> getHaveUserBeans() {
        return haveUserBeans;
    }

    public void setHaveUserBeans(List<HaveUserBean> haveUserBeans) {
        this.haveUserBeans = haveUserBeans;
    }

    public List<TopicInfoBean> getRecommendTopicBeans() {
        return recommendTopicBeans;
    }

    public void setRecommendTopicBeans(List<TopicInfoBean> recommendTopicBeans) {
        this.recommendTopicBeans = recommendTopicBeans;
    }

    public List<RecommendUserBean> getRecommendUserBeans() {
        return recommendUserBeans;
    }

    public void setRecommendUserBeans(List<RecommendUserBean> recommendUserBeans) {
        this.recommendUserBeans = recommendUserBeans;
    }

    public static class HaveUserBean implements Serializable {
        //查询用户后的结果
        String id; //会员编号
        String nickname; //昵称
        String signature;//个性签名
        String avatar_url; //头像的url地址
        String is_followed; //是否已关注

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getIs_followed() {
            return is_followed;
        }

        public void setIs_followed(String is_followed) {
            this.is_followed = is_followed;
        }
    }
}
