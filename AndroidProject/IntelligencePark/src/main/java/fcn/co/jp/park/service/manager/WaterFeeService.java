package fcn.co.jp.park.service.manager;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface WaterFeeService {

    /**
     * 根据水费Id和画面设置内容更新水费
     * @param pd
     * @return
     */
    Map<String, Object> waterFeeEdit(PageData pd) throws Exception;

    /**
     * 获取水费列表信息
     * @return
     */
    List<PageData> getWaterFeeList(PageData pd);

    /**
     * 获取水费列表总件数
     * @return
     */
    Integer getWaterFeeListCount();
}
