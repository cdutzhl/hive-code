package cn.scu.imc.service.conf;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FeignClientConf {
    @Bean
    public Logger.Level setFeignLoglevel(){
        return feign.Logger.Level.FULL;
    }
    @Bean
    public BasicAuthRequestInterceptor getBasicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("admin","cib1234");
    }
}
