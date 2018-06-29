package fcn.co.jp.park.service.me;

import fcn.co.jp.park.model.Picture;
import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:49
 */
public interface CompanyService {
    /**
     * 上传图片路径插入TABLE
     * @param pd
     * @return
     */
    int addImg(PageData pd);

    /**
     * 查询上传图片的ID
     * @param filePath
     * @return
     */
    int selectImgId(String filePath);

    /**
     * 保存公司信息
     * @param pd
     * @return
     */
    Map<String,Object> insertCompanyInfo(PageData pd);

    /**
     * 更新公司信息
     * @param pd
     * @return
     */
    Map<String,Object> updateCompanyInfo(PageData pd);

    /**
     * 查询认证结果
     * @param userId
     * @return
     */
    int selectCompanyAuthResult(String userId);

    /**
     * 添加企业轮播图片
     * @param pd
     * @return
     */
    int insertBanners(PageData pd);
    /**
     * 更新企业轮播图片
     * @param pd
     * @return
     */
    int updateBanners(PageData pd);

    /**
     * 更新企业介绍
     * @param pd
     * @return
     */
    int updateIntroduce(PageData pd);
    /**
     * 查询公司介绍
     * @param userId
     * @return
     */
    String getCompanyIntroduceInfo(String userId);
    /**
     * 查询公司图片
     * @param userId
     * @return
     */
    List<Picture> getCompanyPictureInfo(String userId);

}
