package fcn.co.jp.park.controller.login;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.model.User;
import fcn.co.jp.park.service.UserService;
import fcn.co.jp.park.util.AppUtil;
import fcn.co.jp.park.util.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/userLogin")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    public Object userLogin(){
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = null;
        try {
            returnMap = userService.userLogin(pd);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 通过手机找回密码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendCodeByTel")
    @ResponseBody
    public Object sendCodeByTel() {
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = null;
        try {
            String random = AppUtil.createRandom(true,6);
            returnMap = userService.sendCodeByTel(pd,random);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }

    /**
     * 通过手机重置密码
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editPwdByTel")
    @ResponseBody
    public Object editPwdByTel(){
        PageData pd = this.getPageData();
        Map<String, Object> returnMap = null;
        try {
            returnMap = userService.editPwdByTel(pd);
        } catch (Exception e) {
            returnMap.put("result", false);
            returnMap.put("msg", "系统异常,请稍后再试");
        }
        return returnMap;
    }
}
