package cn.scu.imc.service.user.factory;

import cn.scu.imc.api.vo.ExceptionBack;
import cn.scu.imc.api.vo.User;
import cn.scu.imc.service.user.IUserClientService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserClientserviceFallBack implements FallbackFactory {
    @Override
    public Object create(Throwable throwable) {
        return new IUserClientService() {
            @Override
            public void insertUser(User user) {


            }

            @Override
            public Object getUserById(long id) {
                return generateExceptionBack();
            }

            @Override
            public Object getAll() {
                return generateExceptionBack();
            }
        };
    }

    private ExceptionBack generateExceptionBack(){
        ExceptionBack back = new ExceptionBack();
        back.setServiceName("CLIENT-USER");
        back.setExceptionCode("9001");
        back.setExceptionDesc("客户端对服务调用【CLIENT-USER】进行熔断处理！");
        return back;
    }
}
