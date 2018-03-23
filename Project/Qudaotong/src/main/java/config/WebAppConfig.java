package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import interceptor.UserInterceptor;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
  
  @Bean
  public UserInterceptor userInterceptor() {
    return new UserInterceptor();
  }
  
  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(userInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/userLoginPlus/**", "/logout/**", "/loginPage/**", "/error/**");
    super.addInterceptors(registry);
  }
}