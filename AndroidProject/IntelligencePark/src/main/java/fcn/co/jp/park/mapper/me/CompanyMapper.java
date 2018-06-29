package fcn.co.jp.park.mapper.me;

import fcn.co.jp.park.model.Picture;
import fcn.co.jp.park.util.PageData;

import java.util.List;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:47
 */
public interface CompanyMapper {
    /**
     * 添加图片
     * @param pd
     * @return
     */
    int insertImge(PageData pd);
    /**
     * 获取图片ID
     * @param fileRealPath
     * @return
     */
    int selectImgId(String fileRealPath);

    /**
     * 插入公司信息
     * @param pd
     * @return
     */
    int insertCompanyInfo(PageData pd);
    /**
     * 更新公司信息
     * @param pd
     * @return
     */
    int updateCompanyInfo(PageData pd);


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
     * 查询公司详情
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
