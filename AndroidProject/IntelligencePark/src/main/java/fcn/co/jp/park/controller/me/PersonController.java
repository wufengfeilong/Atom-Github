package fcn.co.jp.park.controller.me;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.me.PersonService;
import fcn.co.jp.park.util.Const;
import fcn.co.jp.park.util.FileUtil;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * create by 860115039
 * date      2018/04/13
 * time      13:46
 * 个人中心-个人信息编辑
 */
@Controller
@RequestMapping(value = "/person")
public class PersonController extends BaseController {
    @Autowired
    PersonService personService;
    @Autowired
    private FileUtil fileUtil;
    /**
     * 获取个人信息
     *
     * @return
     */
    @RequestMapping(value = "/getPersonalInfo")
    @ResponseBody
    public Object getPersonalInfo(@RequestParam(value = "userId") String userId) {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        pd.put("userId",userId);
        try {
            PageData data = personService.getPersonalInfo(pd);
            returnMap.put("data", data);
            returnMap.put("msg", "操作成功");
            returnMap.put("result", true);
        } catch (Exception e) {
            returnMap.put("msg", "系统异常，请稍后再试");
            returnMap.put("result", false);
        }
        return returnMap;
    }

    /**
     * 修改个人信息
     *
     * @return
     */
    @RequestMapping(value = "/updatePersonalInfo")
    @ResponseBody
    public Map<String, Object> updatePersonalInfo(@RequestParam(value = "userId") String userId
            , @RequestParam(value = "name") String name
            , @RequestParam(value = "contactInfo") String contactInfo) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        pd.put("userId",userId);
        pd.put("name",name);
        pd.put("contactInfo",contactInfo);
        try {
            returnMap = personService.updatePersonalInfo(pd);
        } catch (Exception e) {
            returnMap.put("msg", "系统异常，请稍后再试");
            returnMap.put("result", false);
        }
        return returnMap;
    }

    /**
     * 更新头像
     *
     * @param file
     * @param userId
     */
    @RequestMapping(value = "/updateUserPicture")
    @ResponseBody
    public Map<String, Object> updateUserPicture(
            @RequestParam(value = "userPicture", required = false) MultipartFile file,
            @RequestParam(value = "userId") String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        String path = fileUtil.fileUpReturnPath(Const.USER_PICTURE,file);
        PageData pd = new PageData();
        pd.put("userId", userId);
        pd.put("userPicture", path);
        map.put("result", true);
        try {
            int a = personService.updateUserPicture(pd);
            if (a > 0) {
                map.put("msg", "修改头像成功");
                map.put("data", path);
                map.put("result", true);
            }
        } catch (Exception e) {
            map.put("msg", "系统异常，请稍后再试");
            map.put("result", false);
        }
        return map;
    }


}
