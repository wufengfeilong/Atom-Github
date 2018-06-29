package fcn.co.jp.park.model.manager;

/**
 * 管理中心的新闻列表用Model
 */
public class PostNewsModel {

    private Integer newsId;  // 新闻ID

    private String newsTitle; // 新闻标题

    private String newsType; // 新闻标题

    private String newsSources; // 新闻来源

    private String newsContent; // 新闻内容

    private String newsThumbnail; // 新闻图片

    private String updateUser; // 更新者

    private String updateTime; // 更新时间

    private String insertUser; // 追加者

    private String insertTime; // 追加时间

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getNewsSources() {
        return newsSources;
    }

    public void setNewsSources(String newsSources) {
        this.newsSources = newsSources;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsThumbnail() {
        return newsThumbnail;
    }

    public void setNewsThumbnail(String newsThumbnail) {
        this.newsThumbnail = newsThumbnail;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }
}
