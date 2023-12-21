package cn.scu.imc.hiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HiveClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiveClientApplication.class, args);
    }

}
