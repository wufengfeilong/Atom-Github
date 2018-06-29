package fcn.co.jp.park.service.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

/**
 * 广告位管理用Service接口
 */
public interface AdvertisingInfoService {

    /**
     * 获取待审批的广告位列表
     * @return
     */
    Map<String, Object> getAdvertisingApprovalList(PageData pd);

    /**
     * 获取已审批完了的广告位列表
     * @return
     */
    Map<String, Object> getAdvertisingList(PageData pd);

    /**
     * 根据广告Id，获取广告位的详情内容
     * @param pd
     * @return
     */
    Map<String, Object> getAdvertisingDetailInfoById(PageData pd);

    /**
     * 追加广告
     * @return
     */
    Map<String, Object> addAdvertisement(PageData pd);

    /**
     * 点击“通过”按钮后，更新广告的信息
     * @param pd
     * @return
     */
    int updateAdvertisingInfoByPassOn(PageData pd);

    /**
     * 点击“拒绝”按钮后，更新广告的信息
     * @param pd
     * @return
     */
    int updateAdvertisingInfoByRefuse(PageData pd);
}
