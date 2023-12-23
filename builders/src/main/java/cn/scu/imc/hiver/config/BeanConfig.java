package cn.scu.imc.hiver.config;

import cn.scu.imc.hiver.utils.PropertiesUtils;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import java.io.File;
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
    @Bean
    public SslContext  sslContext() throws SSLException {
        String sslPath = String.valueOf(PropertiesUtils.getValueByKey("hive.netty.ssl.server"));
        File certChainFile = new File(sslPath + "server.crt");
        File keyFile=new File(sslPath + "pkcs8_server.key");
        File rootFile=new File(sslPath + "ca.crt");
        return SslContextBuilder
                .forServer(certChainFile,keyFile)
                .trustManager(rootFile)
                .clientAuth(ClientAuth.REQUIRE)
                .build();
    }
}
