package woxingwoxiu.com.home.bean;

import java.util.List;

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

    List<RecommendTopicBean> recommendTopicBeans;
    List<RecommendUserBean> recommendUserBeans;
    List<HaveUserBean> haveUserBeans;

    public List<HaveUserBean> getHaveUserBeans() {
        return haveUserBeans;
    }

    public void setHaveUserBeans(List<HaveUserBean> haveUserBeans) {
        this.haveUserBeans = haveUserBeans;
    }

    public List<RecommendTopicBean> getRecommendTopicBeans() {
        return recommendTopicBeans;
    }

    public void setRecommendTopicBeans(List<RecommendTopicBean> recommendTopicBeans) {
        this.recommendTopicBeans = recommendTopicBeans;
    }

    public List<RecommendUserBean> getRecommendUserBeans() {
        return recommendUserBeans;
    }

    public void setRecommendUserBeans(List<RecommendUserBean> recommendUserBeans) {
        this.recommendUserBeans = recommendUserBeans;
    }

    public static class RecommendTopicBean{
        String title;
        String count;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

    public static class RecommendUserBean{
        String headUrl;
        String nickName;
        String introduce;
        boolean isFollow;

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public boolean isFollow() {
            return isFollow;
        }

        public void setFollow(boolean follow) {
            isFollow = follow;
        }
    }
    public static class HaveUserBean{

         String headUrl;
         String userName;
         String isfollow;

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIsfollow() {
            return isfollow;
        }

        public void setIsfollow(String isfollow) {
            this.isfollow = isfollow;
        }
    }
}
