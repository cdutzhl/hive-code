package cn.scu.imc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.scu.imc")
@EnableCircuitBreaker
public class BuilderApp {
    public static void main(String[] args){
        SpringApplication.run(BuilderApp.class,args);
    }
}
