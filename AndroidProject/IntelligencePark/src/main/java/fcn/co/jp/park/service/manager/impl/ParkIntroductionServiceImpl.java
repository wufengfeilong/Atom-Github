package fcn.co.jp.park.service.manager.impl;

import fcn.co.jp.park.mapper.manager.ParkIntroductionMapper;
import fcn.co.jp.park.service.manager.ParkIntroductionService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service(value = "parkIntroductionService")
public class ParkIntroductionServiceImpl implements ParkIntroductionService {

    @Autowired
    private ParkIntroductionMapper mParkIntroductionMapper;

    @Override
    public Map<String, Object> selectParkInfo(PageData pd) {
        Map<String, Object> returnMap = new HashMap<>();
        PageData param = new PageData();
        String newsId = pd.getString("parkId");
        param.put("parkId", newsId); // 此处的parkId要和给Mapper.xml中传值的字段名保持一致
        PageData result = mParkIntroductionMapper.selectParkInfo(param);
        if (result != null) {
            returnMap.put("data", result);
            return returnMap;
        } else {
            returnMap.put("data", result);
            return returnMap;
        }
    }

    @Override
    public int updateParkInfo(PageData pd) {
        String parkId = pd.getString("parkId");
        if (parkId == "" || parkId == null) {
            mParkIntroductionMapper.insertParkInfo(pd);
            return Integer.valueOf(String.valueOf(pd.get("park_id")));

        } else {
            return mParkIntroductionMapper.updateParkInfo(pd);
        }
    }
}
