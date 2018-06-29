package com.fcn.park.manager.bean;

import java.util.List;

/**
 * Created by 丁胜胜 on 2018/04/25.
 * 类描述：管理中心的留言回复列表用Bean
 */

public class ManagerMessageReplyListBean {

    /**
     * messageList : []
     * totalPage : 0
     */
    private String totalPage;

    private boolean isNext;

    private List<ManagerMessageReplyListBean.ManagerReplyBean> getListMessageReply;

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

    public List<ManagerReplyBean> getGetListMessageReply() {
        return getListMessageReply;
    }

    public void setGetListMessageReply(List<ManagerReplyBean> getListMessageReply) {
        this.getListMessageReply = getListMessageReply;
    }

    public static class ManagerReplyBean {

        private String suggestionId;      //编号
        private String userName;           //当前用户名
        private String describeContent;  //投诉或建议内容
        private String answerContent;    //留言回复内容
        private String updateTimestamp;    //投诉或建议时间
        private String updateUser;          //处理者名字
        private String replyStatus;         //回复成功时候为2

        /**
         * suggestionId：1
         * userName:丁小胜
         * describeContent : 每当三餐做饭时，整条楼道充满着浓浓的烟味，熏呛难忍
         * answerContent : 已经收到，会尽快处理
         * updateTimestamp : 2018-04-28 15:49:48
         * updateUser : 丁丁
         * replyStatus : 2
         */
        public String getReplyStatus() {
            return replyStatus;
        }

        public void setReplyStatus(String replyStatus) {
            this.replyStatus = replyStatus;
        }

        public String getSuggestionId() {
            return suggestionId;
        }

        public void setSuggestionId(String suggestionId) {
            this.suggestionId = suggestionId;
        }

        public String getDescribeContent() {
            return describeContent;
        }

        public void setDescribeContent(String describeContent) {
            this.describeContent = describeContent;
        }

        public String getAnswerContent() {
            return answerContent;
        }

        public void setAnswerContent(String answerContent) {
            this.answerContent = answerContent;
        }

        public String getUpdateTimestamp() {
            return updateTimestamp;
        }

        public void setUpdateTimestamp(String updateTimestamp){
            this.updateTimestamp = updateTimestamp;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;}

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }
    }
}