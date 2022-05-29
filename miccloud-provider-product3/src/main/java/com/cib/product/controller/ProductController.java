package com.cib.product.controller;

import com.cib.api.vo.ExceptionBack;
import com.cib.api.vo.Product;
import com.cib.product.service.IProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Resource
    private IProductService productService;
    @Resource
    private DiscoveryClient client;


    @RequestMapping(value = "/get/{id}")
    @HystrixCommand(fallbackMethod = "getFallBack")
    public Object getProduct(@PathVariable("id") long id){
        Product product= productService.findById(id);
        if(product==null){
            throw new RuntimeException("商品已下架");
        }
        return product;
    }

    public Product getFallBack(@PathVariable("id") long id) {
        Product product = new Product();
        product.setProductId(0L);
        product.setProductName("getProduct-error:id="+id);
        product.setProductDesc("服务提供方：PRODUCT-PROVIDER 错误！");
        return product;
    }
    @RequestMapping("/add")
    public Object add(@RequestBody Product product){
        return productService.createProduct(product);
    }
    @RequestMapping("/list")
    public  Object list(){
        return productService.findAll();
    }
    @RequestMapping("/discovery")
    public Object discovery(){
        return this.client;
    }



}
