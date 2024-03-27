package cn.scu.imc.hiver;


import cn.scu.imc.hiver.bo.vo.Command;
import cn.scu.imc.hiver.netty.rpc.client.ClientBusiHandler;
import cn.scu.imc.hiver.netty.rpc.client.FileClientHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
class RpcNettyClientApplicationTests {

    @Autowired
    private FileClientHandler fileClientHandler;

    @Autowired
    private ClientBusiHandler clientBusiHandler;

    @Test
    void contextLoads() throws InterruptedException {
        long start = System.currentTimeMillis();
        /*发送邮件*/
        Command command = new Command();
        command.setCommands("ping www.baidu.com");
        clientBusiHandler.send(command);
        System.out.println("共耗时："+(System.currentTimeMillis()-start)+"ms");
        Thread.sleep(3000);
    }

    @Test
    void fileUpload() throws InterruptedException, IOException {
        long start = System.currentTimeMillis();
        File file = new File("D:\\workspace\\code\\hive-ci\\hive-ci.zip");
        fileClientHandler.uploadFile(file, "hive-ci");
        System.out.println("共耗时："+(System.currentTimeMillis()-start)+"ms");
        Thread.sleep(3000);
    }

}
