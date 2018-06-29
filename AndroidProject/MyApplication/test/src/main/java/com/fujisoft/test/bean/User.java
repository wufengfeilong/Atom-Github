package com.fujisoft.test.bean;

public class User {

    /**
     * id : 1
     * userName : zfw
     * pwd : 123
     * tel : 13256799736
     */

    private int id;
    private String userName;
    private String pwd;
    private String tel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
