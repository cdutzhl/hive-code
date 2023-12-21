package cn.scu.imc.hiver;

import cn.scu.imc.api.vo.netty.Command;
import cn.scu.imc.hiver.netty.rpc.client.ClientBusiHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RpcNettyClientApplicationTests {
    @Autowired
    private ClientBusiHandler clientBusiHandler;

    @Test
    void contextLoads() throws InterruptedException {
        long start = System.currentTimeMillis();
        /*发送邮件*/
        Command command = new Command();
        command.setCommands("ping www.baidu.com");
        System.out.println("Send mail: "+ clientBusiHandler.send(command));
        System.out.println("共耗时："+(System.currentTimeMillis()-start)+"ms");
        Thread.sleep(3000);
    }

}
