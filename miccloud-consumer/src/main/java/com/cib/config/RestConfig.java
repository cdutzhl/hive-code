package com.cib.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Base64;

@Configuration
public class RestConfig {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
//    @Bean
//    public IRule loadBlance(){
//        return new RandomRule();
//    }

    @Bean
    public HttpHeaders httpHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth="admin:cib@1234";
        byte[] encode = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
        String authHead="Basic "+ new String(encode);
        httpHeaders.add("Authorization",authHead);
        return  httpHeaders;


    }
}
