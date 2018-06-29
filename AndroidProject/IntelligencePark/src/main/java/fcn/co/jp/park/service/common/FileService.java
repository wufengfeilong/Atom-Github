package fcn.co.jp.park.service.common;

import fcn.co.jp.park.util.PageData;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:49
 */
public interface FileService {
    /**
     * 上传图片路径插入TABLE并返回fileId
     * @param pd
     * @return
     */
    int addImg(PageData pd);

    /**
     * 根据fileId获取图片路径
     * @param fileId
     * @return
     */
    String getFilePath(int fileId);


}
