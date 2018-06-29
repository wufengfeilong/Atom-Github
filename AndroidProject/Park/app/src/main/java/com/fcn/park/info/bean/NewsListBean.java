package com.fcn.park.info.bean;

import com.fcn.park.base.http.ParamNames;

import java.util.List;

/**
 * Created by liuyq on 2018/04/10.
 * 类描述：公告新闻活动列表使用bean
 */

public class NewsListBean {


    /**
     * getlistNews : [{"newsId":203,"sortKey":1487668265000,"title":"梁杰：今年1-9月中国服务外包增长一枝独秀",
     * "category":"专题聚焦","news_thumbnail":"uploadFiles/registerImg/5baa93b6422f4f6b91f8f1069277cc9b.jpg",
     * "updateTimeStamp":"2017-02-21","newsSources":"无"},{"newsId":202,"sortKey":1487668186000,"title":"新技术拓宽服务外包领域",
     * "category":"专题聚焦","news_thumbnail":"uploadFiles/registerImg/4407596af1094ce18d503f27b8d46068.jpg",
     * "updateTimeStamp":"2017-02-21","newsSources":"国际商报"},
     * {"newsId":201,"sortKey":1487668105000,"title":"前10月中企签订服务外包合同额增9.5% 商务部解读","category":"专题聚焦",
     * "news_thumbnail":"uploadFiles/registerImg/9cae9e79ae0e416aaeb104a8239bc3cd.jpg","updateTimeStamp":"2017-02-21","newsSources":"中国新闻网"}]
     * totalPage : 1
     * isNext : false
     */

    private String totalPage;
    private boolean isNext;
    private List<GetlistNewsBean> getlistNews;

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isIsNext() {
        return isNext;
    }

    public void setIsNext(boolean isNext) {
        this.isNext = isNext;
    }

    public List<GetlistNewsBean> getGetlistNews() {
        return getlistNews;
    }

    public void setGetlistNews(List<GetlistNewsBean> getlistNews) {
        this.getlistNews = getlistNews;
    }

    public static class GetlistNewsBean {
        /**
         * newsId : 203
         * sortKey : 1487668265000
         * title : 梁杰：今年1-9月中国服务外包增长一枝独秀
         * category : 专题聚焦
         * news_thumbnail : uploadFiles/registerImg/5baa93b6422f4f6b91f8f1069277cc9b.jpg
         * updateTimeStamp : 2017-02-21
         * newsSources : 无
         */

        private String newsId;
        private long sortKey;
        private String title;
        private String category;
        @ParamNames("newsThumbnail")
        private String news_thumbnail;
        private String updateTimeStamp;
        private String newsSources;

        public String getNewsId() {
            return newsId;
        }

        public void setNewsId(String newsId) {
            this.newsId = newsId;
        }

        public long getSortKey() {
            return sortKey;
        }

        public void setSortKey(long sortKey) {
            this.sortKey = sortKey;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getNews_thumbnail() {
            return news_thumbnail;
        }

        public void setNews_thumbnail(String news_thumbnail) {
            this.news_thumbnail = news_thumbnail;
        }

        public String getUpdateTimeStamp() {
            return updateTimeStamp;
        }

        public void setUpdateTimeStamp(String updateTimeStamp) {
            this.updateTimeStamp = updateTimeStamp;
        }

        public String getNewsSources() {
            return newsSources;
        }

        public void setNewsSources(String newsSources) {
            this.newsSources = newsSources;
        }
    }
}
