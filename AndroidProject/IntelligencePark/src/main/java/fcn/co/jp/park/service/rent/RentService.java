package fcn.co.jp.park.service.rent;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

public interface RentService {

    Map<String, Object> getRentAddInitData();

    Map<String, Object> rentInfoEdit(PageData pd);

    Map<String, Object> rentInfoAdd(PageData pd);

    Map<String, Object> rentHouseList(PageData pd);

    Map<String, Object> rentHouseDetail(PageData pd);

    Map<String, Object> rentHouseEditInit(PageData pd);

    Map<String, Object> rentReleasedHouse(PageData pd);
}
