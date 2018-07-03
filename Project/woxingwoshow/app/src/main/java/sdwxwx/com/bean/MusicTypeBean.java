package sdwxwx.com.bean;

/**
 * Created by 860115025 on 2018/06/14.
 */

public class MusicTypeBean {
    /** 类型编号 */
    private String id;
    /** 类型名称 */
    private String name;
    /** 排列顺序。数值越大，排序越靠前 */
    private String sort_order;
    /** 类型图标的URL地址 */
    private String icon_url;
    /** 该类型中的音乐数 */
    private String music_count;
    /** 创建时间 */
    private String create_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMusic_count() {
        return music_count;
    }

    public void setMusic_count(String music_count) {
        this.music_count = music_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }
}
