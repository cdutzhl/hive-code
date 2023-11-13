package cn.scu.imc.zuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizedConf {

    @Bean
    public AuthorizedFilter getAuthorizedFilter(){
        return new AuthorizedFilter();
    }
}
