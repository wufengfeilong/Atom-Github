package fcn.co.jp.park.service.manager.car;
import fcn.co.jp.park.util.PageData;

import java.util.Map;

public interface CarWaitCheckListService {

    /**
     *月租车辆待审批列表
     * @return
     */
    Map<String, Object> selectCarWaitCheckList();

    /**
     * 根据车辆的parkPay_id，获取待审批的月租车辆详情内容
     * @param pd
     * @return
     */
    Map<String, Object> getCarWaitCheckDetailById(PageData pd);

    /**
     * 月租车辆审核通过
     * @param pd
     * @return
     */
    int updateCarWaitCheckPassStatus(PageData pd);

    /**
     *月租车辆审核驳回
     * @param pd
     * @return
     */
    int onTurnClick(PageData pd);
}
