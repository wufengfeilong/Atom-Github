package com.example.demo.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.bean.Result;

/** 
* create by 張風武 
* 2017/09/04 10:35:50 
*/
@ControllerAdvice
public class WholeExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value=Exception.class)
    public Result exceptionHandler(HttpServletRequest req,Exception e){
        System.out.println("WholeExceptionHandler:"+e.getMessage());
        /*
         * 返回json数据或者String数据：
         * 那么需要在方法上加上注解：@ResponseBody
         * 添加return即可。
         */
        return new Result(0, e.getMessage());
        /*
         * 返回视图：
         * 定义一个ModelAndView即可，
         * 然后return;
         * 定义视图文件(比如：error.html,error.ftl,error.jsp);
         *
         */
    }

}
