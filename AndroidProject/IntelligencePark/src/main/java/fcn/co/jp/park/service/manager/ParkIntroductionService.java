package fcn.co.jp.park.service.manager;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

public interface ParkIntroductionService {

    /**
     * 获取园区简介内容
     * @param pd
     * @return
     */
    Map<String, Object> selectParkInfo(PageData pd);

    /**
     * 更新园区简介内容
     * @param pd
     * @return
     */
    int updateParkInfo(PageData pd);
}
