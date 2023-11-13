package cn.scu.imc.user.config;

import cn.scu.imc.user.utils.SSOFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;


@Configuration
public class SSOConfig {

    //配置filter生效
    @Bean
    public FilterRegistrationBean ssoFilterRegistration(RedisTemplate redisTemplate) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SSOFilter(redisTemplate));
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
        registration.setOrder(1);
        return registration;
    }

}
