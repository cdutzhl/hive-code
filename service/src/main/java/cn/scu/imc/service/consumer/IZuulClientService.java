package cn.scu.imc.service.consumer;

import cn.scu.imc.api.vo.User;
import cn.scu.imc.service.conf.FeignClientConf;
import cn.scu.imc.service.consumer.factory.ZuulClientFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "MICCLOUD-ZUUL",configuration =FeignClientConf.class,fallbackFactory = ZuulClientFallBackFactory.class)
public interface IZuulClientService {

    @RequestMapping("/service-api/product-proxy/product/get/{id}")
    public Object getProduct(@PathVariable("id") long id);

    @RequestMapping("/service-api/product-proxy/product/list")
    public Object listProduct();
//
//    @RequestMapping("/service-api/product-proxy/product/add")
//    public Object addProduct(Product product);


    @RequestMapping("/service-api/user-proxy/user/insert")
    public void insertUser(User user);


    @RequestMapping("/service-api/user-proxy/user/getUserById/{id}")
    public Object getUserById(@PathVariable("id") long id);


    @RequestMapping("/service-api/user-proxy/user/getAll")
    public Object getAll();

}
