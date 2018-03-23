package interceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fujisoft.qudao.domain.User;

import util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * description:用户登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor{
    /**
     * 这里做业务处理
     * @param request 请求
     * @param response 响应
     * @param obj
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        //用户登录后，将用户信息放到session域中
        //这里获取session域中的用户
        User user = (User) request.getSession().getAttribute("user");
        //用户未登录
        if(user==null){
            //响应json给前端
            response.setContentType("application/json;charset=utf-8");
            Map<String,Object> map=new HashMap<>();
            map.put("msg","请先登录");
            map.put("ret",0);
            response.getWriter().write(JsonUtils.obj2Json(map));
            return false;
        }
       return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
