package fcn.co.jp.park.mapper.property;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface PropertyParkPayMapper {

    //车辆使用中check
    int checkCarNumber(String carNumber);

    //月租车辆申请添加
    int addParkPayApply(PageData bean);

    //月租车辆页面初始化数据获取
    List<PageData> getInitData(PageData bean);

    //月租车辆驳回画面初始化数据获取
    PageData getRejectInitData(PageData bean);

}
