package fcn.co.jp.park.service.me;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

public interface MeRepairService {
    /**
     * Created by 860117066 on 2018/04/17.
     * 查看个人报修列表
     */
    Map<String,Object> selectRepairRecordList(PageData pd);
    /**
     * Created by 860117066 on 2018/04/17.
     * 查看个人报修详情
     */
    Map<String,Object> getRepairDetailById(PageData pd);
}
