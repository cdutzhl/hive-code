package cn.enjoyedu.rpcreg;

import com.scu.imc.builder.remote.SendSms;
import com.scu.imc.builder.remote.vo.UserInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RpcNettyClientApplicationTests {
    @Autowired
    private SendSms sendSms;

    @Test
    void contextLoads() throws InterruptedException {
        long start = System.currentTimeMillis();
        /*发送邮件*/
        UserInfo userInfo = new UserInfo("四川大学","cdutzhl@163.com");
        System.out.println("Send mail: "+ sendSms.sendMail(userInfo));
        System.out.println("共耗时："+(System.currentTimeMillis()-start)+"ms");
        Thread.sleep(3000);
    }

}