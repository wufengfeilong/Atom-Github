package fcn.co.jp.park.mapper.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface CarWaitCheckListMapper {

    /**
     * 月租车辆待审批列表
     * @return
     */

    List<PageData> selectCarWaitCheckList();

    /**
     *根据车牌号的carNumber，删除相应的新闻条目
     * @param pd
     * @return
     */
    PageData getCarWaitCheckDetailById(PageData pd);

    /**
     * 月租车辆审核通过
     * @param pd
     * @return
     */
    int updateCarWaitCheckPassStatus(PageData pd);

    /**
     * 审核通过,信息表插入数据
     * @param pd
     * @return
     */
    int insertPassMessage(PageData pd);

    /**
     * 月租车辆审核驳回,信息表插入数据
     * @param pd
     * @return
     */
    int insertTurnMessage(PageData pd);

    /**
     * 月租车辆审核驳回
     * @param pd
     * @return
     */
    int onTurnClick(PageData pd);
}
