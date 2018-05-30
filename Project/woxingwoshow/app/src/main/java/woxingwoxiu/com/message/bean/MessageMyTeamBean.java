package woxingwoxiu.com.message.bean;

/**
 *
 * 类描述：我的团队一级二级列表
 */

public class MessageMyTeamBean {
    /*
    * 好友所属团队等级
    * 好友名称
    * 好友注册时间
    * 好友发布次数
    * 好友活跃度
    *
    * */
    private int friendTeamGrade;
    private String friendName;
    private String friendRegTime;
    private String friendPostTimes;
    private String friendActivities;

    public int getFriendTeamGrade() {
        return friendTeamGrade;
    }

    public void setFriendTeamGrade(int friendTeamGrade) {
        this.friendTeamGrade = friendTeamGrade;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendRegTime() {
        return friendRegTime;
    }

    public void setFriendRegTime(String friendRegTime) {
        this.friendRegTime = friendRegTime;
    }

    public String getFriendPostTimes() {
        return friendPostTimes;
    }

    public void setFriendPostTimes(String friendPostTimes) {
        this.friendPostTimes = friendPostTimes;
    }

    public String getFriendActivities() {
        return friendActivities;
    }

    public void setFriendActivities(String friendActivities) {
        this.friendActivities = friendActivities;
    }
}
