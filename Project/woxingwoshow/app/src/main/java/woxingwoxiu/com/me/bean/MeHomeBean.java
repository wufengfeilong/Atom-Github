package woxingwoxiu.com.me.bean;

/**
 * Created by 860117066 on 2018/05/29.
 */

public class MeHomeBean {
    private int userId; //用户id

    private String Head; //粉丝头像url

    private int haveFans; //拥有粉丝数量

    private int haveAttention; //拥有关注数量

    private int teamNumber; //团队数量

    private int moneyNumber; //财富值

    private String fansSex; //性别

    private String birthDate; //出生日期

    private String coverUrl; //封面url

    private String commentNumber; //评论数

    public String getHead() {
        return Head;
    }

    public void setHead(String head) {
        Head = head;
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


}
