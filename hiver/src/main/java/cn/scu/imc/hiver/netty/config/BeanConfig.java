package cn.scu.imc.hiver.netty.config;


import cn.scu.imc.hiver.utils.PropertiesUtils;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import java.io.File;

/**
 * 类说明：
 */
@Configuration
public class BeanConfig {


    @Bean
    public SslContext sslContext() throws SSLException {
        String sslPath = String.valueOf(PropertiesUtils.getValueByKey("hive.netty.ssl.client"));
        //引入SSL安全验证
        File certChainFile = new File(sslPath + "client.crt");
        File keyFile = new File(sslPath + "pkcs8_client.key");
        File rootFile = new File(sslPath + "ca.crt");
        return SslContextBuilder.forClient()
                .keyManager(certChainFile,keyFile)
                .trustManager(rootFile)
                .build();
    }

}
