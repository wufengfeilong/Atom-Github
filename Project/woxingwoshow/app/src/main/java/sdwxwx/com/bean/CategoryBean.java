package sdwxwx.com.bean;

import java.io.Serializable;

/**
 * 栏目信息Bean
 */
public class CategoryBean implements Serializable {

    // 栏目编号。
    private String id;
    // 栏目名称。
    private String name;
    // 排列顺序。数值越大，排序越靠前。
    private int sort_order;
    // 视频数量。
    private int video_count;
    // 栏目的图标
    private String icon_url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public int getVideo_count() {
        return video_count;
    }

    public void setVideo_count(int video_count) {
        this.video_count = video_count;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
}
