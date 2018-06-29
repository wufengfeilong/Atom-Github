package com.fcn.park.manager.bean;

/**
 * 类描述：新闻详情画面用Bean
 */

public class DetailInfoBean {

    /**
     * newsId : 1
     * newsTitle : 济南市十大服务外包企业和成长型服务外包企业评选通知
     * newsThumbnail : "uploadFiles\/registerImg\/ec1fca4d02f744869e747691f72d7476.jpg"
     * newsContent : "<p><span style=\"color: rgb(102, 102, 102); font-family: \" helvetica=\"\" microsoft=\"\" yahei=\"\" font-size:=\"\" background-color:=\"\">协会将接受政府部门和相关机构的委托，开展行业标准制定、企业资质认证等工作。做好政府与企业的桥梁，及时传递政府政策信息，反馈企业动态和需求，协助企业起草项目文件及需政府审核、上报的手续等<\/span><\/p>"
     * newsSources : 外包网
     * updateUser : abc
     * updateTime : 2018-04-13
     * insertUser : abc
     * insertTime : 2018-04-12
     */

    private String newsId;
    private String newsTitle;
    private String newsThumbnail;
    private String newsContent;
    private String newsSources;
    private String updateUser;
    private String updateTime;
    private String insertTime;
    private String insertUser;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
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
