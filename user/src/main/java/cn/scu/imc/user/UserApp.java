package cn.scu.imc.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "cn.scu.imc.user")
@MapperScan("cn.scu.imc.user.mapper")
//@EnableEurekaClient
//@EnableDiscoveryClient
//@EnableCircuitBreaker
public class UserApp {
    public static void main(String[] args){
        SpringApplication.run(UserApp.class,args);
    }
}
