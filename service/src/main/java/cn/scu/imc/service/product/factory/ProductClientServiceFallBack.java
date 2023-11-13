package cn.scu.imc.service.product.factory;

import cn.scu.imc.api.vo.ExceptionBack;
import cn.scu.imc.service.product.IProductClientService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductClientServiceFallBack implements FallbackFactory<IProductClientService> {
    @Override
    public IProductClientService create(Throwable throwable) {
        return new IProductClientService() {
            @Override
            public Object getProduct(long id) {
//                Product product = new Product();
//                product.setProductId(0L);
//                product.setProductName("getProduct-error:id="+id);
//                product.setProductDesc("服务调用方：CONSUMER 错误！");
//                return product;
                return null;
            }

            @Override
            public Object listProduct() {
                return generateException();
            }

//            @Override
//            public Object addProduct(Object product) {
//                return generateException();
//            }
        };
    }

    private ExceptionBack generateException(){
        ExceptionBack exceptionBack = new ExceptionBack();
        exceptionBack.setServiceName("PRODUCT-GET");
        exceptionBack.setExceptionCode("E-10001");
        exceptionBack.setExceptionDesc("查询商品信息错误");
        return exceptionBack;
    }
}
