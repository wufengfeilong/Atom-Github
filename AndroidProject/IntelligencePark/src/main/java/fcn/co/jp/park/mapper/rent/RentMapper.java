package fcn.co.jp.park.mapper.rent;

import fcn.co.jp.park.util.PageData;

import java.util.List;

public interface RentMapper {

    //获取房屋状态列表
    List<PageData> getStatusList(String statusType);

    //获取房屋类型列表
    List<PageData> getHouseTypeList(String houseType);

    //添加房屋
    int rentInfoAdd(PageData pd);

    //编辑房屋
    int rentInfoEdit(PageData pd);

    //获取房屋列表
    List<PageData> rentHouseList(PageData pd);

    //获取房屋详情
    PageData rentHouseDetail(PageData pd);

    int getHouseCount(PageData pd);

    //获取已发布房屋列表
    List<PageData> rentReleasedHouseList(PageData pd);

}
