package woxingwoxiu.com.message.bean;

/**
 * Created by 860117066 on 2018/05/17.
 */

public class MessageWealthBean {
    /*id
    * 财富类型（推荐财富、视频财富）
    * 财富总值
    * 今日财富总值
    * 财富标题
    * 创造财富时间
    * 本次创造财富值
    * 视频财富图片
    * */
        private int id;
        private String wealthType;
        private String wealthSum;
        private String wealthToday;
        private String wealthTime;
        private String wealthTitle;
        private String wealthCount;
        private String wealthPic;

    public String getWealthType() {
        return wealthType;
    }

    public void setWealthType(String wealthType) {
        this.wealthType = wealthType;
    }

    public String getWealthSum() {
        return wealthSum;
    }

    public void setWealthSum(String wealthSum) {
        this.wealthSum = wealthSum;
    }

    public String getWealthToday() {
        return wealthToday;
    }

    public void setWealthToday(String wealthToday) {
        this.wealthToday = wealthToday;
    }

    public String getWealthTime() {
        return wealthTime;
    }

    public void setWealthTime(String wealthTime) {
        this.wealthTime = wealthTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWealthTitle() {
        return wealthTitle;
    }

    public void setWealthTitle(String wealthTitle) {
        this.wealthTitle = wealthTitle;
    }

    public String getWealthCount() {
        return wealthCount;
    }

    public void setWealthCount(String wealthCount) {
        this.wealthCount = wealthCount;
    }

    public String getWealthPic() {
        return wealthPic;
    }

    public void setWealthPic(String wealthPic) {
        this.wealthPic = wealthPic;
    }
}
