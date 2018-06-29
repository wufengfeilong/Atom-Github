package fcn.co.jp.park.controller.property;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.property.PropertyRepairService;
import fcn.co.jp.park.service.property.PropertySuggestionService;
import fcn.co.jp.park.util.Const;
import fcn.co.jp.park.util.FileUtil;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 投诉建议controller
 */
@Controller
@RequestMapping(value = "/SuggestionInfo")
public class PropertySuggestionController extends BaseController {
    @Autowired
    private PropertySuggestionService propertySuggestionService;

    /**
     * 追加评价建议信息
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/sendSuggestion", produces = {"application/json;charset=UTF-8"})
    private Object sendSuggestion() {
        PageData pd = this.getPageData();
        pd.put("describeContent", pd.getString("describeContent"));
        pd.put("userId", pd.getString("userId"));
        Map<String, Object> resultMap = propertySuggestionService.saveSuggestion(pd);
        return resultMap;
    }
}
