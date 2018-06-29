package fcn.co.jp.park.mapper.property;

import fcn.co.jp.park.util.PageData;

public interface PropertySuggestionMapper {

    /**
     * 添加用户建议
     * @return
     */
    int insertSuggestion(PageData pd);
}
