package cn.scu.imc.hiver.netty.config;


import cn.scu.imc.hiver.netty.rpc.RpcClientFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 类说明：
 */
@Configuration
public class BeanConfig {

    @Autowired
    private RpcClientFrame rpcClientFrame;

}
