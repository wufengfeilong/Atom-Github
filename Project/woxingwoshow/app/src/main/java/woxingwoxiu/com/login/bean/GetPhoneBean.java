package woxingwoxiu.com.login.bean;

import java.util.ArrayList;

/**
 * Created by 丁胜胜 on 2018/05/29.
 */

public class GetPhoneBean {

    private String header;
    private String footer;
    private ArrayList<ChildEntity> children;
    private ArrayList<ChildEntityTwo> childrenTwo;

    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public ArrayList<ChildEntity> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ChildEntity> children) {
        this.children = children;
    }

    public ArrayList<ChildEntityTwo> getChildrenTwo() {
        return childrenTwo;
    }

    public void setChildrenTwo(ArrayList<ChildEntityTwo> childrenTwo) {
        this.childrenTwo = childrenTwo;
    }
}
