package cn.scu.imc.controller;

import cn.scu.imc.api.vo.Product;
import cn.scu.imc.service.product.IProductClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/consumer1")
public class ConsumerProductController {

    Logger logger = LoggerFactory.getLogger(ConsumerProductController.class);

    @Resource
    private IProductClientService productService;




    @RequestMapping("/product/get/{id}")
    public Object getProduct(@PathVariable("id")  long id){

        return productService.getProduct(id);
    }
    @RequestMapping("/product/list")
    public Object listProduct(){
        return productService.listProduct();
    }

    @RequestMapping("/product/add")
    public Object listProduct(Product product){
        return productService.addProduct(product);
    }


}
