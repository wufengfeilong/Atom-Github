package woxingwoxiu.com.message.bean;

/**
 *
 * 类描述：好友资料
 */

public class MessageFriendInformationBean {
    /*好友名称、
    * 好友粉丝数
    * 好友关注数
    * 好友团队数
    * 好友财富值、
    * 好友地址
    * 好友性别
    * 好友描述
    * 送出钻石数
    * 发布视频数
    * */
    private String friendName;
    private String friendFanscount;
    private String friendAttentioncount;
    private String friendTeamcount;
    private String friendWealth;
    private String friendAddress;
    private String friendSex;
    private String friendDescribe;
    private String friendSendDiamond;
    private String friendSendmovie;

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendFanscount() {
        return friendFanscount;
    }

    public void setFriendFanscount(String friendFanscount) {
        this.friendFanscount = friendFanscount;
    }

    public String getFriendAttentioncount() {
        return friendAttentioncount;
    }

    public void setFriendAttentioncount(String friendAttentioncount) {
        this.friendAttentioncount = friendAttentioncount;
    }

    public String getFriendTeamcount() {
        return friendTeamcount;
    }

    public void setFriendTeamcount(String friendTeamcount) {
        this.friendTeamcount = friendTeamcount;
    }

    public String getFriendWealth() {
        return friendWealth;
    }

    public void setFriendWealth(String friendWealth) {
        this.friendWealth = friendWealth;
    }

    public String getFriendAddress() {
        return friendAddress;
    }

    public void setFriendAddress(String friendAddress) {
        this.friendAddress = friendAddress;
    }

    public String getFriendSex() {
        return friendSex;
    }

    public void setFriendSex(String friendSex) {
        this.friendSex = friendSex;
    }

    public String getFriendDescribe() {
        return friendDescribe;
    }

    public void setFriendDescribe(String friendDescribe) {
        this.friendDescribe = friendDescribe;
    }

    public String getFriendSendDiamond() {
        return friendSendDiamond;
    }

    public void setFriendSendDiamond(String friendSendDiamond) {
        this.friendSendDiamond = friendSendDiamond;
    }

    public String getFriendSendmovie() {
        return friendSendmovie;
    }

    public void setFriendSendmovie(String friendSendmovie) {
        this.friendSendmovie = friendSendmovie;
    }
}
