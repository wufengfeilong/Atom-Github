package fcn.co.jp.park.mapper.common;

import fcn.co.jp.park.util.PageData;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:47
 */
public interface FileMapper {

    /**
     * 添加图片
     * @param pd
     * @return
     */
    int insertImge(PageData pd);

    /**
     * 获取图片路径
     * @param fileId
     * @return
     */
    String getFilePath(int fileId);

}
