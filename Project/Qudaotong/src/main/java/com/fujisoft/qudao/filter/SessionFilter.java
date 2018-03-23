package com.fujisoft.qudao.filter;

import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by  on 2018/03/06.
 *
 * 过滤器
 */
public class SessionFilter implements Filter {
  private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SessionFilter.class);


  /**
   * 封装，不需要过滤的list列表
   */
  protected static List<Pattern> patterns = new ArrayList<Pattern>();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
    logger.info("aaaaaaaaaa");
    String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
    if (url.startsWith("/") && url.length() > 1) {
      url = url.substring(1);
    }

    if (isInclude(url)){
      chain.doFilter(httpRequest, httpResponse);
      return;
    } else {
      HttpSession session = httpRequest.getSession();
      if (session.getAttribute("") != null){
        // session存在
        chain.doFilter(httpRequest, httpResponse);
        return;
      } else {
        // session不存在 准备跳转失败
        /* RequestDispatcher dispatcher = request.getRequestDispatcher(path);
          dispatcher.forward(request, response);*/
        chain.doFilter(httpRequest, httpResponse);
        return;
      }
    }


  }

  @Override
  public void destroy() {

  }


  /**
   * 是否需要过滤
   * @param url
   * @return
   */
  private boolean isInclude(String url) {
    for (Pattern pattern : patterns) {
      Matcher matcher = pattern.matcher(url);
      if (matcher.matches()) {
        return true;
      }
    }
    return false;
  }
  /**
   * 配置过滤器
   * @return
   */
  @Bean
  public FilterRegistrationBean someFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(sessionFilter());
    registration.addUrlPatterns("/*");
    registration.addInitParameter("paramName", "paramValue");
    registration.setName("sessionFilter");
    return registration;
  }

  /**
   * 创建一个bean
   * @return
   */
  @Bean(name = "sessionFilter")
  public Filter sessionFilter() {
    return new SessionFilter();
  }
}
