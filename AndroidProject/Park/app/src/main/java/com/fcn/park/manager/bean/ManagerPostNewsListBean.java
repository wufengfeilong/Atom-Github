package com.fcn.park.manager.bean;

import java.util.List;

/**
 * 类描述：新闻、公告、活动列表用Bean
 */
public class ManagerPostNewsListBean {


    /**
     * getlistNews : [{"newsId":203,"sortKey":1487668265000,"title":"梁杰：今年1-9月中国服务外包增长一枝独秀","category":"专题聚焦","news_thumbnail":"uploadFiles/registerImg/5baa93b6422f4f6b91f8f1069277cc9b.jpg","updateTimeStamp":"2017-02-21","newsSources":"无"},{"newsId":202,"sortKey":1487668186000,"title":"新技术拓宽服务外包领域","category":"专题聚焦","news_thumbnail":"uploadFiles/registerImg/4407596af1094ce18d503f27b8d46068.jpg","updateTimeStamp":"2017-02-21","newsSources":"国际商报"},{"newsId":201,"sortKey":1487668105000,"title":"前10月中企签订服务外包合同额增9.5% 商务部解读","category":"专题聚焦","news_thumbnail":"uploadFiles/registerImg/9cae9e79ae0e416aaeb104a8239bc3cd.jpg","updateTimeStamp":"2017-02-21","newsSources":"中国新闻网"}]
     * totalPage : 1
     * isNext : false
     */

    private String totalPage;

    private boolean isNext;

    private List<ListNewsBean> listNews;

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

    public List<ListNewsBean> getListNews() {
        return listNews;
    }

    public void setListNews(List<ListNewsBean> listNews) {
        this.listNews = listNews;
    }

    public static class ListNewsBean {
        /**
         * newsId : 203
         * category : 新闻、公告、活动类型的区分
         * sortKey : 1487668265000
         * title : 梁杰：今年1-9月中国服务外包增长一枝独秀
         * category : 专题聚焦
         * news_thumbnail : uploadFiles/registerImg/5baa93b6422f4f6b91f8f1069277cc9b.jpg
         * updateTimeStamp : 2017-02-21
         * newsSources : 无
         */

        private String newsId;
        private String category;
        private String newsTitle;
        private String newsThumbnail;
        private String newsContent;
        private String newsSources;
        private String updateUser;
        private String updateTime;
        private String insertUser;
        private String insertTime;

        public String getNewsId() {
            return newsId;
        }

        public void setNewsId(String newsId) {
            this.newsId = newsId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getNewsTitle() {
            return newsTitle;
        }

        public void setNewsTitle(String newsTitle) {
            this.newsTitle = newsTitle;
        }

        public String getNewsThumbnail() {
            return newsThumbnail;
        }

        public void setNewsThumbnail(String newsThumbnail) {
            this.newsThumbnail = newsThumbnail;
        }

        public String getNewsContent() {
            return newsContent;
        }

        public void setNewsContent(String newsContent) {
            this.newsContent = newsContent;
        }

        public String getNewsSources() {
            return newsSources;
        }

        public void setNewsSources(String newsSources) {
            this.newsSources = newsSources;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getInsertUser() {
            return insertUser;
        }

        public void setInsertUser(String insertUser) {
            this.insertUser = insertUser;
        }

        public String getInsertTime() {
            return insertTime;
        }

        public void setInsertTime(String insertTime) {
            this.insertTime = insertTime;
        }
    }
}
