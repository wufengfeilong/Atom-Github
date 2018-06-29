package fcn.co.jp.park.mapper.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;

/**
 * 广告位管理用Mapper接口
 */
public interface AdvertisingInfoMapper {

    /**
     * 获取待审批的广告位列表
     * @return
     */
    List<PageData> getAdvertisingApprovalList(PageData pd);

    /**
     * 获取已审核广告列表条数
     * @return
     */
    int selectAdvertisingApprovalCount();

    /**
     * 获取已审批完了的广告位列表
     * @return
     */
    List<PageData> getAdvertisingList();

    /**
     * 根据广告Id，获取广告位的详情内容
     * @param pd
     * @return
     */
    PageData getAdvertisingDetailInfoById(PageData pd);

    /**
     * 追加广告
     * @param pd
     * @return
     */
    int addAdvertisement(PageData pd);

    /**
     * 点击“通过”按钮后，更新广告的信息、并向Message表中插入展示给用户的信息
     * @param pd
     * @return
     */
    int updateAdvertisingInfoByPassOn(PageData pd);
    int insertOKMsg(PageData pd);
    int insertNGMsg(PageData pd);
    /**
     * 点击“拒绝”按钮后，更新广告的信息
     * @param pd
     * @return
     */
    int updateAdvertisingInfoByRefuse(PageData pd);

    /**
     * 获取已审核广告列表条数
     * @return
     */
    int getAdvertisementInfoCount();
}
