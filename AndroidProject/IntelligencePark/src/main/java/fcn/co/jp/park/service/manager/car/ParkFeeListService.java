package fcn.co.jp.park.service.manager.car;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

public interface ParkFeeListService {

    /**
     *停车付费列表
     * @return
     */
    Map<String, Object> selectParkFeeList();

    /**
     * 根据车辆的parkPay_id，获取停车付费详情内容
     * @param pd
     * @return
     */
    Map<String, Object> getParkFeeListDetailById(PageData pd);
}
