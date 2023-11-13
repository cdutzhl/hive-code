package cn.scu.imc.service.consumer.factory;

import cn.scu.imc.api.vo.ExceptionBack;
import cn.scu.imc.api.vo.User;
import cn.scu.imc.service.consumer.IZuulClientService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ZuulClientFallBackFactory implements FallbackFactory<IZuulClientService> {

    private ExceptionBack getExceptionBack(String serviceName){
        ExceptionBack exceptionBack = new ExceptionBack();
        exceptionBack.setServiceName(serviceName);
        exceptionBack.setExceptionCode("90001");
        exceptionBack.setExceptionDesc("服务端调用异常，请稍后再试试！");
        return exceptionBack;
    }
    @Override
    public IZuulClientService create(Throwable throwable) {


        return new IZuulClientService() {
            @Override
            public Object getProduct(long id) {
                return getExceptionBack("PRODUCT-SERVICE-GET-PRODUCT");
            }

            @Override
            public Object listProduct() {
                return getExceptionBack("PRODUCT-SERVICE-LIST-PRODUCT");
            }

//            @Override
//            public Object addProduct(Product product) {
//                return getExceptionBack("PRODUCT-SERVICE-ADD-PRODUCT");
//            }

            @Override
            public void insertUser(User user) {

            }

            @Override
            public Object getUserById(long id) {
                return  getExceptionBack("USER-SERVICE-GET-USER");
            }

            @Override
            public Object getAll() {
                return  getExceptionBack("USER-SERVICE-GETALL-USER");
            }
        };
    }
}
