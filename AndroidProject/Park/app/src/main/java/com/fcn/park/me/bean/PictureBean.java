package com.fcn.park.me.bean;

/**
 * create by 860115039
 * date      2018/04/20
 * time      14:34
 * 图片上传用Bean
 */
public class PictureBean {
    String id;        //图片id
    String path;   //图片存储路径
    String userId;
    //图片是否变化
    boolean flg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isFlg() {
        return flg;
    }

    public void setFlg(boolean flg) {
        this.flg = flg;
    }
}
