package com.fcn.park.info.bean;

/**
 * Created by liuyq on 2017/04/12.
 * 类描述：公告、园区新闻、园区活动页面
 */

public class InfoNewsBean {

    /**
     * updateDate : 2017-02-23
     * title : 济南法律咨询 房产/合同/婚姻/继承/公司法律顾问
     * content :
     */

    private String updateDate;
    private String title;
    private String insertTimestamp;
    private String sources;
    private String content;
    private String updateTimestamp;

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(String insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }
}
