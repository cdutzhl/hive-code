package cn.scu.imc.hiver.config;

import cn.scu.imc.hiver.utils.PropertiesUtils;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLException;
import java.io.File;

@Configuration
public class BeanConfig {

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
