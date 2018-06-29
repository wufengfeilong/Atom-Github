package com.fcn.park.me.bean;

/**
 * create by 860115039
 * date      2018/04/17
 * time      10:13
 * 个人中心-个人信息编辑
 */
public class MePersonInfoBean {

    private String userPicture;//头像地址
    private String name = "";//名字
    private String contactInfo = "";//联系电话


    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }


}
