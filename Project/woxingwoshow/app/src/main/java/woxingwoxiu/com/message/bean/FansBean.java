package woxingwoxiu.com.message.bean;

/**
 * Created by 860117073 on 2018/5/16.
 * 我的粉丝的bean
 */

public class FansBean {

    private int userId; //用户id

    private String fansHead; //粉丝头像url

    private int haveFans; //拥有粉丝数量

    private int haveAttention; //拥有关注数量

    private int teamNumber; //团队数量

    private int moneyNumber; //财富值

    private boolean isfollowed; //是否互相关注

    private String fansSex; //性别

    private String birthDate; //出生日期

    private String coverUrl; //封面url

    private String commentNumber; //评论数

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(String commentNumber) {
        this.commentNumber = commentNumber;
    }

    public String getFansSex() {
        return fansSex;
    }

    public void setFansSex(String fansSex) {
        this.fansSex = fansSex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getFansHead() {
        return fansHead;
    }

    public void setFansHead(String fansHead) {
        this.fansHead = fansHead;
    }

    public int getHaveFans() {
        return haveFans;
    }

    public void setHaveFans(int haveFans) {
        this.haveFans = haveFans;
    }

    public int getHaveAttention() {
        return haveAttention;
    }

    public void setHaveAttention(int haveAttention) {
        this.haveAttention = haveAttention;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public int getMoneyNumber() {
        return moneyNumber;
    }

    public void setMoneyNumber(int moneyNumber) {
        this.moneyNumber = moneyNumber;
    }

    public boolean isIsfollowed() {
        return isfollowed;
    }

    public void setIsfollowed(boolean isfollowed) {
        this.isfollowed = isfollowed;
    }
}
