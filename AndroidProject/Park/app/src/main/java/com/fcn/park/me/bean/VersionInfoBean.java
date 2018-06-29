package com.fcn.park.me.bean;

/**
 * create by 860115039
 * date      2018/04/25
 * time      14:02
 * 个人中心-版本更新
 */
public class VersionInfoBean {
//    {
        //app名字
        // appname: "Park_2.0.apk",
        // 服务器版本号
        // serverVersion: "2",
        // 服务器标志 serverFlag: "1",
        // 是否强制更新 lastForce: "1",
        // apk下载地址，这里我已经下载了官方的apk，放到了服务器里面
        // updateurl: "http://172.29.140.35:8080/PARK/images/Park_2.0.apk",
        // 版本的更新的描述
        // upgradeinfo: "V2.0版本更新，你想不想要试一下哈！！！"
        // }
    // app名字
    public String appname;
    //服务器版本
    public String serverVersion;
    //服务器标志
    public String serverFlag;
    //强制升级 0 不强制；1，强制
    public String lastForce;
    //app最新版本地址
    public String updateurl;
    //升级信息
    public String upgradeinfo;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getServerFlag() {
        return serverFlag;
    }

    public void setServerFlag(String serverFlag) {
        this.serverFlag = serverFlag;
    }

    public String getLastForce() {
        return lastForce;
    }

    public void setLastForce(String lastForce) {
        this.lastForce = lastForce;
    }

    public String getUpdateurl() {
        return updateurl;
    }

    public void setUpdateurl(String updateurl) {
        this.updateurl = updateurl;
    }

    public String getUpgradeinfo() {
        return upgradeinfo;
    }

    public void setUpgradeinfo(String upgradeinfo) {
        this.upgradeinfo = upgradeinfo;
    }
}
