package com.fcn.park.me.bean;

import java.util.List;

/**
 * create by 860115039
 * date      2018/04/23
 * time      13:23
 * 个人中心-我的消息-消息列表
 */
public class MeMessageRecordBean {
    private String totalPage;
    private boolean isNext;
    private List<MeMessageRecordBean.ListMessageBean> listMessageBean;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }

    public List<ListMessageBean> getListMessageBean() {
        return listMessageBean;
    }

    public void setListMessageBean(List<ListMessageBean> listMessageBean) {
        this.listMessageBean = listMessageBean;
    }

    public static class ListMessageBean {
        private String id;//id
        private String title;//标题
        private String content;//内容
        private String activityType;//类型
        private String userId;//被通知用户id，
        private String creator;//创建者
        private int readStatus;//已读未读
        private String creatTime;//创建时间

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
                case "8":
                    resultStr = "企业认证";
                    break;
                case "9":
                    resultStr = "全体通知";
                    break;
                case "10":
                case "11":
                    resultStr = "广告费";
                    break;
            }
            return resultStr;
        }

        public int getReadStatus() {
            return readStatus;
        }

        public void setReadStatus(int readStatus) {
            this.readStatus = readStatus;
        }
    }
}
