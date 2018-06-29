package com.fcn.park.info.bean;

import com.fcn.park.base.http.ParamNames;

/**
 * Created by liuyq on 2018/4/10.
 * 类描述：公告新闻活动企业需求title使用bean
 */

public class NewsTypeBean {

    /**
     * distinguishId : 0
     * value : 公告
     */

    private String distinguishId;
    @ParamNames("distinguishValue")
    private String value;


    public String getDistinguishId() {
        return distinguishId;
    }

    public void setDistinguishId(String distinguishId) {
        this.distinguishId = distinguishId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
