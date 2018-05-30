package woxingwoxiu.com.bean;

import java.io.Serializable;

/**
 * 话题/栏目Bean
 */

public class CollectionBean implements Serializable {

    // 栏目编号
    private int collectionId;

    // 栏目名称
    private String collectionName;

    // 排列顺序。数值越大，排序越靠前。
    private int sortOrder;

    // 视频数量
    private int videoCount;

    // 是否已禁用。已禁用的栏目在客户端不可见。
    private boolean isDisabled = false;

    /**
     * @return 栏目编号
     */
    public int getCollectionId() {
        return collectionId;
    }

    /**
     * @param collectionId 栏目编号
     */
    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    /**
     * @return 栏目名称
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * @param collectionName 栏目名称
     */
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    /**
     * @return 排列顺序
     */
    public int getSortOrder() {
        return sortOrder;
    }

    /**
     * @param sortOrder 排列顺序
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * @return 视频数量
     */
    public int getVideoCount() {
        return videoCount;
    }

    /**
     * @param videoCount 视频数量
     */
    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    /**
     * @return 是否已禁用
     */
    public boolean isDisabled() {
        return isDisabled;
    }

    /**
     * @param disabled 是否已禁用
     */
    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
    }

}
