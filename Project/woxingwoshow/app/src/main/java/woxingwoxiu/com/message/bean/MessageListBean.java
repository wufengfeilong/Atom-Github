package woxingwoxiu.com.message.bean;

import java.util.List;


/**
 * Created by 860117073 on 2018/5/11.
 */

public class MessageListBean {

    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public static class Result {

        private int type; //布局类型

        private List<String> imageUrl; //用户头像或作品封面

        private String userName; //用户名称

        private String isAttention; //是否关注

        private String actionNumber; //播放数量

        private String fansNumber; //粉丝数量

        private String assistantContent; //助手内容

        private String passTime; //经过时间

        private boolean isfollowed; //是否关注

        public boolean isIsfollowed() {
            return isfollowed;
        }

        public void setIsfollowed(boolean isfollowed) {
            this.isfollowed = isfollowed;
        }

        public String getPassTime() {
            return passTime;
        }

        public void setPassTime(String passTime) {
            this.passTime = passTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<String> getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(List<String> imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIsAttention() {
            return isAttention;
        }

        public void setIsAttention(String isAttention) {
            this.isAttention = isAttention;
        }

        public String getActionNumber() {
            return actionNumber;
        }

        public void setActionNumber(String actionNumber) {
            this.actionNumber = actionNumber;
        }

        public String getFansNumber() {
            return fansNumber;
        }

        public void setFansNumber(String fansNumber) {
            this.fansNumber = fansNumber;
        }

        public String getAssistantContent() {
            return assistantContent;
        }

        public void setAssistantContent(String assistantContent) {
            this.assistantContent = assistantContent;
        }
    }
}
