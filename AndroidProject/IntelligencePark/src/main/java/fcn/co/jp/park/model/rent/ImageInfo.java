package fcn.co.jp.park.model.rent;

public class ImageInfo {

    //图片ID
    public String imageID;

    //图片path
    public String imagePath;

    //图片更改Flg
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
