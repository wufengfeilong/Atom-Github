package fcn.co.jp.park.service.property;

import fcn.co.jp.park.util.PageData;

import java.util.List;
import java.util.Map;

public interface PropertyParkPayService {

    int checkCarNumber(String carNumber);

    Map<String,Object> addparkPayApply(PageData pd);

    Map<String, Object> getInitData();

    Map<String, Object> getRejectInitData(PageData pd);
}
