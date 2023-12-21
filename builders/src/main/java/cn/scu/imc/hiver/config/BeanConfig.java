package cn.scu.imc.hiver.config;

import cn.scu.imc.hiver.utils.PropertiesUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class BeanConfig {
    @Bean
    public ThreadPoolExecutor taskExecutor(){
        Integer maxThreads = Integer.valueOf(PropertiesUtils.getValueByKey("hive.netty.exectutor.max_threads") + "");
        return new ThreadPoolExecutor(maxThreads, maxThreads, 0L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
    }
}
