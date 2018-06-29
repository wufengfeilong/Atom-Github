package fcn.co.jp.park.service.property.impl;

import fcn.co.jp.park.mapper.property.PropertySuggestionMapper;
import fcn.co.jp.park.service.property.PropertySuggestionService;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service(value = "propertySuggestionService")
public class PropertySuggestionServiceImpl implements PropertySuggestionService {


    @Autowired
    private PropertySuggestionMapper propertySuggestionMapper;

    /**
     * 提交评价建议
     * @param pd
     * @return
     */

    @Override
    public Map<String, Object> saveSuggestion(PageData pd) {

        int result = propertySuggestionMapper.insertSuggestion(pd);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (result > 0) {
            returnMap.put("result", true);
            returnMap.put("msg", "感谢您的建议和意见");
        } else {
            returnMap.put("result", false);
            returnMap.put("msg", "出了点小问题");
        }
        return returnMap;
    }
}
