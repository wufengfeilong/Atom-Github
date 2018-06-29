package com.fcn.park.me.bean;

/**
 * create by 860115039
 * date      2018/04/26
 * time      15:23
 * 个人中心-消息详情
 */
public class MeMsgDetailBean {
    private String id;//id
    private String title;//标题
    private String content;//内容
    private String activityType;//类型
    private String userId;//被通知用户id，
    private String creator;//创建者
    private String creatTime;//创建时间
    private int readStatus;//已读未读
    private String payId;//付费id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getMsgTypeText(){
        String resultStr = "";
        switch (activityType){
            case "":
                resultStr = "";
                break;
            case "1":
                resultStr = "水费";
                break;
            case "2":
                resultStr = "电费";
                break;
            case "3":
                resultStr = "物业费";
                break;
            case "4":
                resultStr = "租赁费";
                break;
            case "5":
                resultStr = "停车费";
                break;
            case "6":
                resultStr = "临时停车费";
                break;
            case "7":
                resultStr = "认证失败";
                break;
            case "8":
                resultStr = "认证成功";
                break;
            case "9":
                resultStr = "全体通知";
                break;
            case "10":
                resultStr = "广告费";
                break;
        }
        return resultStr;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }
}
