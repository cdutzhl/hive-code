package cn.scu.imc.hiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EntityScan(basePackages = "cn.scu.imc.api.vo")
@SpringBootApplication(scanBasePackages = "cn.scu.imc.hiver")
//@EnableEurekaClient
//@EnableDiscoveryClient
//@EnableCircuitBreaker
public class HiverApp {
    public static void main(String[] args){
        SpringApplication.run(HiverApp.class,args);
    }
}
