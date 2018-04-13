package com.fujisoft.crypto.bean;
/** 
* create by 張風武 
* 2018/02/23 11:15:42 
*/
public class User {
    int id;
    String name;
    String pwd;
    
    public User() {
        super();
    }

    public User(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
