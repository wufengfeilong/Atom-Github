package fcn.co.jp.park.mapper.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface ParkFeeListMapper {

    /**
     * 月租车辆待审批列表
     * @return
     */

    List<PageData> selectParkFeeList();

    /**
     *根据车牌号的carNumber，删除相应的新闻条目
     * @param pd
     * @return
     */
    PageData getParkFeeListDetailById(PageData pd);
}
