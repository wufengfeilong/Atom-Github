package fcn.co.jp.park.controller;

import fcn.co.jp.park.model.User;
import fcn.co.jp.park.util.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 得到PageData
     *
     * @return
     */
    public PageData getPageData() {
        return new PageData(this.getRequest());
    }

    /**
     * 得到ModelAndView
     *
     * @return
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    /**
     * 得到request对象
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        return request;
    }

    /**
     * 得到当前登录用户
     *
     * @param type
     *            Const.SESSION_USER  Const.PLAT_USER
     * @return
     */
    public User getUser(String type) {
        User user = new User();
//        Subject currentUser = SecurityUtils.getSubject();
//        Session session = currentUser.getSession();
//        if (Const.SESSION_USER.equals(type)) {
//            // 后台用户
//            user = (User) session.getAttribute(Const.SESSION_USER);
//        } else {
//            // 前台用户
//            user = (User) session.getAttribute(Const.PLAT_USER);
//        }
        return user;
    }

    /**
     * 得到32位的uuid
     *
     * @return
     */
    public String get32UUID() {

        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }


    public static void logBefore(Logger logger, String interfaceName) {
        logger.info("");
        logger.info("start");
        logger.info(interfaceName);
    }

    public static void logAfter(Logger logger) {
        logger.info("end");
        logger.info("");
    }
}
