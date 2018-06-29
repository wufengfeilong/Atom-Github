package com.fcn.park.rent.bean;

/**
 * Created by 860617024 on 25/04/2018.
 */

public class ImageInfo {

    public String imageID;

    public String imageUploadID;

    public String imagePath;

    public boolean changeFlg;


    public ImageInfo(String imageID, String imagePath, boolean changeFlg) {
        this.imageID = imageID;
        this.imagePath = imagePath;
        this.changeFlg = changeFlg;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImageUploadID() {
        return imageUploadID;
    }

    public void setImageUploadID(String imageUploadID) {
        this.imageUploadID = imageUploadID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isChangeFlg() {
        return changeFlg;
    }

    public void setChangeFlg(boolean changeFlg) {
        this.changeFlg = changeFlg;
    }
}
