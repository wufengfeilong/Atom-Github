package fcn.co.jp.park.service.property;

import fcn.co.jp.park.util.PageData;

import java.util.Map;

public interface PropertySuggestionService {

    /**
     * 保存评价建议
     * @param pd
     * @return
     */
    Map<String, Object> saveSuggestion(PageData pd);
}
