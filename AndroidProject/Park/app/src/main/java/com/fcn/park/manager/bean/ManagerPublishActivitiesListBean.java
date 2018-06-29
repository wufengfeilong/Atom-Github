package com.fcn.park.manager.bean;

import java.util.List;

/**
 * 管理中心的活动列表用Bean.
 */
public class ManagerPublishActivitiesListBean {

    /**
     * getlistNews : [{"newsId":203,"sortKey":1487668265000,"title":"梁杰：今年1-9月中国服务外包增长一枝独秀","category":"专题聚焦","news_thumbnail":"uploadFiles/registerImg/5baa93b6422f4f6b91f8f1069277cc9b.jpg","updateTimeStamp":"2017-02-21","newsSources":"无"},{"newsId":202,"sortKey":1487668186000,"title":"新技术拓宽服务外包领域","category":"专题聚焦","news_thumbnail":"uploadFiles/registerImg/4407596af1094ce18d503f27b8d46068.jpg","updateTimeStamp":"2017-02-21","newsSources":"国际商报"},{"newsId":201,"sortKey":1487668105000,"title":"前10月中企签订服务外包合同额增9.5% 商务部解读","category":"专题聚焦","news_thumbnail":"uploadFiles/registerImg/9cae9e79ae0e416aaeb104a8239bc3cd.jpg","updateTimeStamp":"2017-02-21","newsSources":"中国新闻网"}]
     * totalPage : 1
     * isNext : false
     */
    private String totalPage;
    private List<PublishActivitiesListBean> publishActivitiesListBean;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public List<PublishActivitiesListBean> getPublishActivitiesListBean() {
        return publishActivitiesListBean;
    }

    public void setPublishActivitiesListBean(List<PublishActivitiesListBean> publishActivitiesListBean) {
        this.publishActivitiesListBean = publishActivitiesListBean;
    }

    public static class PublishActivitiesListBean {

        /**
         * newsId : 203
         * sortKey : 1487668265000
         * title : 梁杰：今年1-9月中国服务外包增长一枝独秀
         * category : 专题聚焦
         * news_thumbnail : uploadFiles/registerImg/5baa93b6422f4f6b91f8f1069277cc9b.jpg
         * updateTimeStamp : 2017-02-21
         * newsSources : 无
         */
        private String activitiesId;
        private String activitiesTitle;
        private String activitiesContent;
        private String activitiesSources;
        private String activitiesThumbnail;
        private String updateTime;
        private String updateUser;
        private String insertTime;
        private String insertUser;

        public String getActivitiesId() {
            return activitiesId;
        }

        public void setActivitiesId(String activitiesId) {
            this.activitiesId = activitiesId;
        }

        public String getActivitiesTitle() {
            return activitiesTitle;
        }

        public void setActivitiesTitle(String activitiesTitle) {
            this.activitiesTitle = activitiesTitle;
        }

        public String getActivitiesContent() {
            return activitiesContent;
        }

        public void setActivitiesContent(String activitiesContent) {
            this.activitiesContent = activitiesContent;
        }

        public String getActivitiesSources() {
            return activitiesSources;
        }

        public void setActivitiesSources(String activitiesSources) {
            this.activitiesSources = activitiesSources;
        }

        public String getActivitiesThumbnail() {
            return activitiesThumbnail;
        }

        public void setActivitiesThumbnail(String activitiesThumbnail) {
            this.activitiesThumbnail = activitiesThumbnail;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getInsertTime() {
            return insertTime;
        }

        public void setInsertTime(String insertTime) {
            this.insertTime = insertTime;
        }

        public String getInsertUser() {
            return insertUser;
        }

        public void setInsertUser(String insertUser) {
            this.insertUser = insertUser;
        }
    }
}
