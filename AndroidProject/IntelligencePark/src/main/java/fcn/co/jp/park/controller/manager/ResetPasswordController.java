package fcn.co.jp.park.controller.manager;

import fcn.co.jp.park.controller.BaseController;
import fcn.co.jp.park.service.manager.ResetPasswordService;
import fcn.co.jp.park.util.MD5;
import fcn.co.jp.park.util.PageData;
import fcn.co.jp.park.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员重置登录密码
 */
@Controller
@RequestMapping(value = "/managerInfo")
public class ResetPasswordController extends BaseController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @ResponseBody
    @RequestMapping(value = "/resetManagerPassword", produces = {"application/json;charset=UTF-8"})
    public Object resetPasswordByPhoneAndPsd() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        PageData pd = this.getPageData();
        try {
            if (!StringUtil.isEmpty(pd.getString("oldPwd")) || !StringUtil.isEmpty(pd.getString("newPwd"))) {
                pd.put("oldPwd", MD5.md5(pd.getString("oldPwd")));
                pd.put("newPwd", MD5.md5(pd.getString("newPwd")));
            }
            PageData oldPwd = resetPasswordService.findPwdById(pd);
            pd.put("userName", oldPwd.getString("userName"));
            if (oldPwd.getString("pwd").equals(pd.getString("oldPwd"))) {
                int count = resetPasswordService.resetPwd(pd);
                if (count > 0) {
                    returnMap.put("msg", "密码重置成功");
                    returnMap.put("result", true);
                } else {
                    returnMap.put("msg", "修改失败，请稍后重试");
                    returnMap.put("result", false);
                }
            } else {
                returnMap.put("msg", "旧密码错误");
                returnMap.put("result", false);
            }
        } catch (Exception e) {
            returnMap.put("msg", "系统异常，请稍后再试");
            returnMap.put("result", false);
        }
        return returnMap;
    }
}
