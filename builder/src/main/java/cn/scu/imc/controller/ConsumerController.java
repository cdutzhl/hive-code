package cn.scu.imc.controller;

import cn.scu.imc.api.vo.Product;
import cn.scu.imc.service.consumer.IZuulClientService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Resource
    private IZuulClientService zuulClientService;

    @RequestMapping("/product/get/{id}")
    public Object getProduct(@PathVariable("id")  long id){

        return zuulClientService.getProduct(id);
    }
    @RequestMapping("/product/list")
    public Object listProduct(){

        return zuulClientService.listProduct();
    }

    @RequestMapping("/product/add")
    public Object listProduct(Product product){
        return zuulClientService.addProduct(product);
    }



    @RequestMapping("/user/getAll")
    public Object getAll(){
        return zuulClientService.getAll();
    }
    @RequestMapping("/user/getUserById/{id}")
    public Object getUserById(@PathVariable("id") long id){
        return zuulClientService.getUserById(id);
    }
    @RequestMapping("/getUserAndProduct/{id}")
    public Object getUserAndProduct(@PathVariable("id") long id){
        Map<String,Object> map = new HashMap<>();
        map.put("user",zuulClientService.getUserById(id));
        map.put("product",zuulClientService.getProduct(id));
       return map;
    }

}
