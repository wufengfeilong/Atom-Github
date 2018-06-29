package com.fcn.park.login.bean;

import com.fcn.park.base.constant.Constant;
import com.fcn.park.base.http.ParamNames;

import java.io.Serializable;

/**
 * Created by 860115001 on 2018/04/10.
 */

public class User implements Serializable {

    @ParamNames("user_name")
    private String userName;
    @ParamNames("userType")
    private String userType = Constant.UserType.PERSON.getValue();
    @ParamNames("img_path")
    private String avatar;
    @ParamNames("user_id")
    private String token;

    private boolean groupRole;
    private boolean personalRole;
    private boolean sysUserRole;

    public boolean isGroupRole() {
        return groupRole;
    }

    public void setGroupRole(boolean groupRole) {
        this.groupRole = groupRole;
    }

    public boolean isPersonalRole() {
        return personalRole;
    }

    public void setPersonalRole(boolean personalRole) {
        this.personalRole = personalRole;
    }

    public boolean isSysUserRole() {
        return sysUserRole;
    }

    public void setSysUserRole(boolean sysUserRole) {
        this.sysUserRole = sysUserRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
