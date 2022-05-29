package com.cib;

import com.cdut.config.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication(scanBasePackages = "com.cib")
@EnableEurekaClient
//@RibbonClient(name ="MICCLOUD-PROVIDER-PRODUCT" ,configuration = RibbonConfig.class)
public class ConsumerApp {
    public static void main(String[] args){
        SpringApplication.run(ConsumerApp.class,args);
    }
}
